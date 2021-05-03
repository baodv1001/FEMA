<?php
	$hostname= "localhost";
	$username = "root";
	$password = "";
	$databasename = "fashionecommercemobileapp";
	
	$con = mysqli_connect($hostname,$username,$password,$databasename);

		$idAccount = $_POST['idAccount'] ?? "";
   		$idProduct = $_POST['idProduct'] ?? "";
		$idWishlist = '';
		
	if (strlen($idAccount) > 0 && strlen($idProduct) > 0)
	{
		$query1 = "SELECT * FROM wishlist
			WHERE idAccount = '$idAccount' AND idWishlist IN (SELECT idwishlist FROM wishlistinfo
                                          			WHERE idProduct = '$idProduct');";

		$check = mysqli_query($con,$query1);

		while ($row = $check->fetch_row())
		{
			$idWishlist = $row[0];
		}

		$query2 = "DELETE FROM wishlistinfo WHERE idProduct = '$idProduct'
						AND idwishlist = '$idWishlist'";
		mysqli_query($con,$query2);

		$query3 = "DELETE FROM wishlist WHERE idAccount = '$idAccount'
						AND idwishlist = '$idWishlist'";
		mysqli_query($con,$query3);

		echo "Success";

	} else echo "Failed";

?>