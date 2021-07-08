<?php
 include_once '../dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();

		$idAccount= $_POST['idAccount'] ?? "";
   		$idProduct = $_POST['idProduct'] ?? "";
		//$idAccount = '32';
		//$idProduct = '1';
		
	if (strlen($idProduct) > 0 && strlen($idAccount) > 0)
	{
		$query1 = "SELECT * FROM wishlist
			WHERE idAccount = '$idAccount';";

		$check = mysqli_query($db->connect(),$query1);

		if (mysqli_num_rows($check) > 0)
		{	
			while ($row = $check->fetch_row())
			{
				$idWishlist = $row[0];
			}

			$query2 = "SELECT * FROM WISHLISTINFO WHERE IDWISHLIST = '$idWishlist' AND IDPRODUCT = '$idProduct'";
			$result = mysqli_query($db->connect(),$query2);
			
			if (mysqli_num_rows($result) > 0)
			{
				echo "Success";
			} else {
				echo "Failed";
			}
		} else 
		{
			echo "Failed";
		}
	} else echo "Failed";
 }
 dispInfo();
?>