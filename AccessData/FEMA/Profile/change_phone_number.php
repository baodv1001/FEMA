<?php
    include_once '../dbConnect.php';
    function changePhoneNumber() {
        $db = new dbConnect();

        $idAccount = $_REQUEST['idAccount'];
        $phoneNumber = $_REQUEST['phoneNumber'];

        $result = mysqli_query($db->Connect(), 
        "UPDATE user 
        SET `phoneNumber`= '$phoneNumber'
        WHERE `idAccount` = $idAccount;");

        echo json_encode($result);
    }
    changePhoneNumber()
?>