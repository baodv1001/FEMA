<?php
    include_once '../dbConnect.php';
    function getIdAddress() {
        $db = new dbConnect();

        $response = array();
        $idAccount = $_REQUEST['idAccount'];
        $name = $_REQUEST['name'];
        $address = $_REQUEST['address'];
        $phoneNumber = $_REQUEST['phoneNumber'];

        $result = mysqli_query($db->connect(),
        "SELECT idAddress from addressinfo 
        WHERE idAccount = '1' AND name = 'Quang' AND address = 'Quảng Ninh' AND phoneNumber = '1234567890';");
        while($row = mysqli_fetch_assoc($result)){
            $t = array();
            $t["idAddress"] = $row["idAddress"];
            // Mảng JSON
            array_push($response, $t);
          }
        
        
        header("Content-type: Json");
        echo (int) json_encode(array_values($response));
    }
    getIdAddress()
?>