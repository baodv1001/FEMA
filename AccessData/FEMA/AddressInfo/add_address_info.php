<?php
    include_once '../dbConnect.php';
    function addAddressInfo() {
        $db = new dbConnect();

        $idAccount = $_REQUEST['idAccount'];
        $name = $_REQUEST['name'];
        $address = $_REQUEST['address'];
        $phoneNumber = $_REQUEST['phoneNumber'];

        $result = mysqli_query($db->connect(),
        "INSERT INTO addressinfo (idAccount, name, address, phoneNumber)
        VALUES ($idAccount, '$name', '$address', '$phoneNumber');");

        header("Content-type: JSON");
        echo json_encode($result);
    }
    addAddressInfo();
?>