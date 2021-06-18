<?php
    include_once '../dbConnect.php';
    function changePassWord(){
        $db = new dbConnect();
        // Mảng JSON
        $idAccount = $_REQUEST['idAccount'];
        $newPassword = $_REQUEST['newPassword'];
        
        // Câu lệnh select dùng để xem dữ liệu
        $result = mysqli_query($db->connect(), 
        "UPDATE account 
        SET `password` = '$newPassword'
        WHERE `idAccount` = '$idAccount';");

        echo "success";
    }
    changePassWord();
?>