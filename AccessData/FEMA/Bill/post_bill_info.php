<?php
  include_once '../dbConnect.php';
  function createBillInfo() {
    $db = new dbConnect();

    $idBill = $_REQUEST['idBill'];
    $idProduct = $_REQUEST['idProduct'];
    $quantity = $_REQUEST['quantity'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO billinfo (idBill, idProduct, quantity) 
      VALUES ('$idBill', '$idProduct', '$quantity')");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createBillInfo();
?>