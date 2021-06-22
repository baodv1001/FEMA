<?php
 include_once './dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
	
	$idAccount = $_REQUEST['idAccount'] ?? "";
	$query = "SELECT * FROM product
			WHERE idProduct IN (SELECT A.idProduct FROM wishlistinfo AS A
			WHERE A.idWishList IN (SELECT B.idWishList FROM wishlist AS B
			WHERE B.idAccount ='$idAccount'))";
	$resouter = mysqli_query($db->connect(),$query);
 
	$temparray = array();
	$total_records = mysqli_num_rows($resouter);
 
	if ($total_records >= 1) {
    		while ($row = mysqli_fetch_assoc($resouter)) {
			$data = array();
      $data['idProduct']= $row['idProduct'];
			$data['idProductCode']= $row['idProductCode'];
			$data['name']= $row['name'];
			$data['price']= $row['price'];
			$data['quantity']= $row['quantity'];
			$data['unit']= $row['unit'];
			$data['rating']= $row['rating'];
			$data['imageFile']= $row['imageFile'];
			$data['discount']= $row['discount'];
      $data["description"] = $row["description"];
      $data['isDeleted']= $row['isDeleted'];
			
			array_push($temparray, $data);
   		} 
	
}   
header("Content-Type: Json");
echo json_encode($temparray);
 }
dispInfo();
?>