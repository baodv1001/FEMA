<?php
  include_once '../dbConnect.php';
  function getCart() {
    $db = new dbConnect();

    $idAccount = $_REQUEST['idAccount'];
    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM cart WHERE idAccount = $idAccount and isPaid = 0");

    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idCart"] = $row["idCart"];
      $data["idAccount"] = $row["idAccount"];
      $data["isPaid"] = $row["isPaid"];
      
      array_push($response, $data);
    }
    header("Content-Type: Json");
    echo json_encode($response[0]);
  }
  getCart();
?>