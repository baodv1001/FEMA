<?php
  include_once '../dbConnect.php';
  function createBillInfo() {
    $db = new dbConnect();

    $idBill = $_REQUEST['idBill'];
    $idProduct = $_REQUEST['idProduct'];
    $idSize = $_REQUEST['idSize'];
    $idColor = $_REQUEST['idColor'];
    $quantity = $_REQUEST['quantity'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO billinfo (idBill, idProduct, idSize, idColor, quantity) 
      VALUES ('$idBill', '$idProduct', '$idSize', '$idColor', '$quantity')");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createBillInfo();
?>