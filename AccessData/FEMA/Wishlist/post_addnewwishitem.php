<?php
 include_once '../dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();

		$idAccount = $_POST['idAccount'] ?? "";
   		$idProduct = $_POST['idProduct'] ?? "";
		$idWishlist = '';
		
	if (strlen($idAccount) > 0 && strlen($idProduct) > 0)
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

			$query2 = "INSERT INTO wishlistinfo VALUES ('$idWishlist', '$idProduct')";
			$result = mysqli_query($db->connect(),$query2);
			
			if ($result)
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