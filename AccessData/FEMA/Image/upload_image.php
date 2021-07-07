<?php
    include_once '../dbConnect.php';
    function upLoadImage() {
        $db = new dbConnect();

        $uploadpath = 'http://laptop-0qnm76ck/fashionecommerceapp/Image/'; 
        $file_path = $uploadpath.basename( $_FILES['uploaded_file']['name']);
        
        if(!is_dir($file_path)){
            //Directory does not exist, so lets create it.
            mkdir($file_path, 0755, true);
        }
        if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
            echo "success";
        } else{
            echo "fail";
        }
    }
    upLoadImage();
?>