<?php
    include_once '../dbConnect.php';
    function updateUser() {
        $db = new dbConnect();

        $idAccount = $_REQUEST['idAccount'];
        $name = $_REQUEST['name'];
        $gender = $_REQUEST['gender'];
        $dateOfBirth = $_REQUEST['dateOfBirth'];

        $result = mysqli_query($db->Connect(), 
        "UPDATE user 
        SET `name`= '$name', `gender` = '$gender', `dateOfBirth` = '$dateOfBirth'
        WHERE `idAccount` = $idAccount;");

        echo json_encode($result);
    }
    updateUser()
?>