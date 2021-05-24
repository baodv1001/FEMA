<?php
  include_once '../dbConnect.php';
  function getCartInfo() {
    $db = new dbConnect();
    
    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];
    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM cartinfo 
      WHERE idCart = $idCart and idProduct = $idProduct");
    
    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idCart"] = $row["idCart"];
      $data["idProduct"] = $row["idProduct"];
      $data["quantity"] = $row["quantity"];
      
      array_push($response, $data);
    }
    
    header("Content-Type: Json");
    echo json_encode($response);
  }
  getCartInfo();
?>