<?php
 include_once '../dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();

		$idAccount = $_POST['idAccount'] ?? "";
   		$idProduct = $_POST['idProduct'] ?? "";
		$idWishlist = '';
		$idCart = '';
		$quantity = '';
		
	if (strlen($idAccount) > 0 && strlen($idProduct) > 0)
	{
		$query1 = "SELECT * FROM wishlist WHERE idAccount = '$idAccount';";

		$check = mysqli_query($db->connect(),$query1);

		while ($row = $check->fetch_row())
		{
			$idWishlist = $row[0];
		}

		$query1 = "SELECT * FROM cart WHERE idAccount = '$idAccount';";

		$check = mysqli_query($db->connect(),$query1);

		while ($row = $check->fetch_row())
		{
			$idCart = $row[0];
		}
		
		$deletewishitem = "DELETE FROM wishlistinfo where idProduct = '$idProduct' and idWishlist = '$idWishlist' ";

		mysqli_query($db->connect(), $deletewishitem);

		$addcartitem = "INSERT INTO cartinfo VALUES('$idCart', '$idProduct', '$quantity')";

		$result = mysqli_query($db->connect(), $addcartitem);
			
		if (!$result)
			{
				$updateCartItem = "UPDATE cartinfo SET quantity = quantity + 1 WHERE idCart = '$idCart' AND idProduct = '$idProduct'";
				mysqli_query($db->connect(), $updateCartItem);
			}
		echo "Success";
	} else echo "Failed";
 }
 dispInfo();
?>