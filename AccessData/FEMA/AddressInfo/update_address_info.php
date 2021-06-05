<?php
    include_once '../dbConnect.php';
    function editAddressInfo() {
        $db = new dbConnect();

        $name = $_REQUEST['name'];
        $address = $_REQUEST['address'];
        $phoneNumber = $_REQUEST['phoneNumber'];
        $idAddress = $_REQUEST['idAddress'];

        $result = mysqli_query($db->connect(),
        "UPDATE `addressinfo` 
        SET `name` = '$name', `address` = '$address', `phoneNumber` = '$phoneNumber'
        WHERE `idAddress` = '$idAddress';");

        echo json_encode($result);
    }
    editAddressInfo();
?>