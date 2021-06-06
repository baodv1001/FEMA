<?php
  include_once '../dbConnect.php';
  function getCartInfo() {
    $db = new dbConnect();
    
    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];
    $idSize = $_REQUEST['idSize'];
    $idColor = $_REQUEST['idColor'];

    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM cartinfo 
      WHERE idCart = '$idCart' and idProduct = '$idProduct' 
      and idSize = '$idSize' and idColor = '$idColor'");
    
    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idCart"] = $row["idCart"];
      $data["idProduct"] = $row["idProduct"];
      $data["idSize"] = $row["idSize"];
      $data["idColor"] = $row["idColor"];
      $data["quantity"] = $row["quantity"];
      
      array_push($response, $data);
    }

    if(count($response) == 0) {
      $data = array();
      $data["idCart"] = '0';
      $data["idProduct"] = '0';
      $data["idSize"] = '0';
      $data["idColor"] = '0';
      $data["quantity"] = '0';
      array_push($response, $data);
    }
    header("Content-Type: Json");
    echo json_encode($response[0]);
  }
  getCartInfo();
?>