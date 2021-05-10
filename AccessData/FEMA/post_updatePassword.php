<?php
	$hostname= "localhost";
	$username = "root";
	$password = "";
	$databasename = "fashionecommercemobileapp";
	
	$con = mysqli_connect($hostname,$username,$password,$databasename);

		$username = $_POST['username'] ?? "";
   		$password = $_POST['password'] ?? "";
		$phoneNumber = $_POST['phoneNumber'] ?? "";
		
	if (strlen($phoneNumber) > 0 && strlen($username) > 0 && strlen($password) > 0)
	{
		$idAccount ='';
		$query1 = "SELECT * FROM account
			where idAccount in (SELECT A.idAccount FROM user AS A
                    	WHERE A.phoneNumber = '$phoneNumber');";

		$check = mysqli_query($con,$query1);

		while ($row = $check->fetch_row())
		{
			$idAccount = $row[0];
		}
		$query2 = "UPDATE account SET password = '$password' WHERE idAccount = '$idAccount'";
		mysqli_query($con,$query2);
		echo "Success";

	} else echo "Failed";

?>