<?php
	$hostname= "localhost";
	$username = "root";
	$password = "";
	$databasename = "fashionecommercemobileapp";
	
	$con = mysqli_connect($hostname,$username,$password,$databasename);

		$username = $_POST['username'] ?? "";
		$phoneNumber = $_POST['phoneNumber'] ?? "";

	if (strlen($phoneNumber) > 0 && strlen($username) > 0)
	{
		$query1 = "SELECT * FROM account
			where idAccount in (SELECT A.idAccount FROM user AS A
                    	WHERE A.phoneNumber = '$phoneNumber')
			AND username = '$username';";

		$check = mysqli_query($con,$query1);

		if (mysqli_num_rows($check) > 0) {
			echo "true";
		} else {
			echo "false";	
		}
	} else echo "Null";

?>