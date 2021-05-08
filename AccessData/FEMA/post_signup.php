<?php
	$hostname= "localhost";
	$username = "root";
	$password = "";
	$databasename = "fashionecommercemobileapp";
	
	$con = mysqli_connect($hostname,$username,$password,$databasename);

		$username = $_POST['username'] ?? "";
   		$password = $_POST['password'] ?? "";
		$name= $_POST['name'] ?? "";
		$phoneNumber = $_POST['phoneNumber'] ?? "";

	if (strlen($phoneNumber) > 0 && strlen($name) && strlen($username) > 0 && strlen($password) > 0)
	{
		$query1 = "SELECT * FROM account
			where idAccount in (SELECT A.idAccount FROM user AS A
                    	WHERE A.phoneNumber = '$phoneNumber');";

		$check = mysqli_query($con,$query1);

		if (mysqli_num_rows($check) == 0) {
			$query2 = "INSERT INTO account(username, password) VALUES ('$username','$password');";
			$result = mysqli_query($con,$query2);

				if ($result) {
					$last_id = mysqli_insert_id($con);
					$query3 = "INSERT INTO user(name, phoneNumber, idAccount) VALUES ('$name','$phoneNumber','$last_id');";
					$data = mysqli_query($con,$query3);

					if ($data) {
						echo "Success";
					} else {
						echo "Failed";
					}

				} else {
					echo "FailedUsername";
				}

		} else {
			echo "FailedPhone";	
		}
	} else echo "Null";

?>