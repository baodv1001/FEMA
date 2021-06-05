<?php
    include_once '../dbConnect.php';
    function deleteAddressInfo() {
        $db = new dbConnect();

        $idAccount = $_REQUEST['idAccount'];
        $name = $_REQUEST['name'];
        $address = $_REQUEST['address'];
        $phoneNumber = $_REQUEST['phoneNumber'];

        $result = mysqli_query($db->connect(),
        "DELETE FROM addressinfo WHERE idAccount = $idAccount
                                AND name = '$name'
                                AND address = '$address'
                                AND phoneNumber = '$phoneNumber';");

        header("Content-type: JSON");
        echo json_encode($result);
    }
    deleteAddressInfo()
?>