<?php
    include_once '../dbConnect.php';
    function checkPhoneNumber() {
        $db = new dbConnect();

        $phoneNumber = $_REQUEST['phoneNumber'];
        $response = array();
        //Câu lệnh Select dùng để xem dữ liệu
        $result = mysqli_query($db->connect(), "SELECT * FROM user WHERE `phoneNumber` = '$phoneNumber';");
        //Đọc dữ liệu từ MySQL
        if (mysqli_num_rows($result) > 0) {
			echo "true";
		} else {
			echo "false";	
		}
    }
    checkPhoneNumber();
?>