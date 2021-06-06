<?php
  include_once '../dbConnect.php';
  function getCartInfo() {
    $db = new dbConnect();
    
    // $idCart = $_REQUEST['idCart'];
    $idCart = '1';
    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM cartinfo WHERE idCart = $idCart");
    
    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idCart"] = $row["idCart"];
      $data["idProduct"] = $row["idProduct"];
      $data["idSize"] = $row["idSize"];
      $data["idColor"] = $row["idColor"];
      $data["quantity"] = $row["quantity"];
      
      array_push($response, $data);
    }
    
    header("Content-Type: Json");
    echo json_encode($response);
  }
  getCartInfo();
?>