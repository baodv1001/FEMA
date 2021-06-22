<?php
    include_once '../dbConnect.php';
    function login(){
        $db = new dbConnect();
        // Mảng JSON
        $username = $_REQUEST['username'];
        $password = $_REQUEST['password'];
        $response = array();
        // Câu lệnh select dùng để xem dữ liệu
        $result = mysqli_query($db->connect(), "SELECT * FROM Account WHERE username = '$username' and password = '$password';");
        while($row = mysqli_fetch_assoc($result)) {
            $t = array();
            $t["idAccount"] = $row["idAccount"];
            $t["username"] = $row["username"];
            $t["password"] = $row["password"];
            $t["type"] = $row["type"];
            $t["isDeleted"] = $row["isDeleted"];
            array_push($response,$t);
        }
        header("Content-Type: JSON");
        echo json_encode($response);
    }
    login();
?>