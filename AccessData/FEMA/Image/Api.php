<?php 

    //getting database connection
	include_once '../dbConnect.php';
    $db = new dbConnect();
    //array to show the response
    $response = array();
    
    //uploads directory, we will upload all the files inside this folder
    $target_dir = "";

    $idAccount = $_REQUEST['idAccount'];

    //checking if we are having an api call, using the get parameters 'apicall'
    if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
            
            //if the api call is for uploading the image 
            case 'upload':
                //error message and error flag
                $message = 'Params ';
                $is_error = false; 
                
                //validating the request to check if all the required parameters are available or not 
                if(!isset($_POST['desc'])){
                    $message .= "desc, ";
                    $is_error = true; 
                }

                if(!isset($_FILES['image']['name'])){
                    $message .= "image ";
                    $is_error = true; 
                }
                
                //in case we have an error in validation, displaying the error message 
                if($is_error){
                    $response['error'] = true; 
                    $response['message'] = $message . " required."; 
                }else{
                    //if validation succeeds 
                    //creating a target file with a unique name, so that for every upload we create a unique file in our server
                    $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['image']['name'], PATHINFO_EXTENSION);
                    
                    //saving the uploaded file to the uploads directory in our target file
                    if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
                        
                        echo "1";
                        //saving the file information to our database
                        $result = mysqli_query($db->connect(), 
                            "UPDATE user 
                            SET `image` = 'http://192.168.224.188/FEMA/Image/$target_file'
                            WHERE `idAccount` = $idAccount;" );
                        //$stmt = $conn->prepare("INSERT INTO uploads (`path`, `description`) VALUES (?, ?)");
                        //$stmt->bind_param("ss", $target_file, $_POST['desc']);

                        //if it is saved in database successfully
                        if($result){
                            //displaying success response
                            $response['error'] = false; 
                            $response['message'] = 'Image Uploaded Successfully';
                            $response['image'] = getBaseURL() . $target_file;    
                        }else{
                            //if not saved in database
                            //showing response accordingly
                            $response['error'] = true; 
                            $response['message'] = 'Could not upload image, try again...'; 
                        }
                        $stmt->close(); 
                    } else {
                        $response['error'] = true; 
                        $response['message'] = 'Try again later...';
                    }
                }
            break;
            
            //we will use this case to get all the uploaded images from the database 
            case 'images':
                $stmt = $conn->prepare("SELECT `id`, `path`, `description` FROM uploads");
                $stmt->execute();
                $stmt->bind_result($id, $path, $desc);
                
                while($stmt->fetch()){
                    $image = array(); 
                    $image['id'] = $id; 
                    $image['path'] = getBaseURL() . $path; 
                    $image['desc'] = $desc;
                    array_push($response, $image);
                }

            break; 
			
			default: 
				$response['error'] = true; 
				$response['message'] = 'Invalid Operation Called';
		}
		
	}else{
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
    }

    function getBaseURL(){
        $url  = isset($_SERVER['HTTPS']) ? 'https://' : 'http://';
        $url .= $_SERVER['SERVER_NAME'];
        $url .= $_SERVER['REQUEST_URI'];
        return dirname($url) . '/';
    }
    

    header('Content-Type: application/json');
    //echo json_encode($response);
?>