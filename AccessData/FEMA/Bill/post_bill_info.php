<?php
  include_once '../dbConnect.php';
  function createBillInfo() {
    $db = new dbConnect();

    $idBill = $_POST['idBill'];
    $idProduct = $_POST['idProduct'];
    $idSize = $_POST['idSize'];
    $idColor = $_POST['idColor'];
    $quantity = $_POST['quantity'];
    $price = $_POST['price'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO billinfo (idBill, idProduct, idSize, idColor, quantity, price) 
      VALUES ('$idBill', '$idProduct', '$idSize', '$idColor', '$quantity', $price)");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createBillInfo();
?>