<?php
  include_once '../dbConnect.php';
  function getBillInfo() {
    $db = new dbConnect();
    
    $idBill = $_REQUEST['idBill'];
    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM billinfo WHERE idBill = $idBill");
    
    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idBill"] = $row["idBill"];
      $data["idProduct"] = $row["idProduct"];
      $data["idSize"] = $row["idSize"];
      $data["idColor"] = $row["idColor"];
      $data["quantity"] = $row["quantity"];
      $data["price"] = $row["price"];
      
      array_push($response, $data);
    }
    
    header("Content-Type: Json");
    echo json_encode($response);
  }
  getBillInfo();
?>