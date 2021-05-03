<?php
	$hostname= "localhost";
	$username = "root";
	$password = "";
	$databasename = "fashionecommercemobileapp";
	
	$con = mysqli_connect($hostname,$username,$password,$databasename);
	
	$idAccount = $_REQUEST['idAccount'] ?? "";
	$idAccount ='32';	

	$query = "SELECT * FROM product
			WHERE idProduct IN (SELECT A.idProduct FROM wishlistinfo AS A
			WHERE A.idWishList IN (SELECT B.idWishList FROM wishlist AS B
			WHERE B.idAccount ='$idAccount'))";
	$resouter = mysqli_query($con,$query);
 
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
			$data['isDeleted']= $row['isDeleted'];
			
			array_push($temparray, $data);
   		} 
	
}   
header("Content-Type: Json");
echo json_encode($temparray);


?>