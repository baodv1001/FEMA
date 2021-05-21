<?php
    include_once './dbConnect.php';
    function dispInfo() {
        $db = new dbConnect();
        //Mảng JSON
        $idAccount = $_REQUEST['idAccount'];
        $response = array();
        //Câu lệnh Select dùng để xem dữ liệu
        $result = mysqli_query($db->connect(), "SELECT * FROM user WHERE idAccount = '$idAccount';");
        //Đọc dữ liệu từ MySQL
        while($row = mysqli_fetch_assoc($result)){
            $t = array();
            $t["id"] = $row["idUser"];
            $t["name"] = $row["name"];
            $t["gender"] = $row["gender"];
            $t["phoneNumber"] = $row["phoneNumber"];
            $t["dateOfBirth"] = $row["dateofBirth"];
            $t["idAccount"] = $row["idAccount"];
            $t["image"] = $row["image"];
            $t["idNumber"] = $row["idNumber"];
            $t["email"] = $row["email"];
            $t["isDeleted"] = $row["isDeleted"];
            //Mảng JSON
            array_push($response, $t);
        }
        //Thiết lập header là JSON
        header("Content-Type: JSON");
        //Hiển thị kết quả
        echo json_encode($response);
    }
    dispInfo();
?>