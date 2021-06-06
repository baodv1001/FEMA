<?php
  include_once '../dbConnect.php';
  function createCartInfo() {
    $db = new dbConnect();

    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];
    $idSize = $_REQUEST['idSize'];
    $idColor = $_REQUEST['idColor'];
    $quantity = $_REQUEST['quantity'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO cartinfo (idCart, idProduct, idSize, idColor, quantity) 
      VALUES ('$idCart', '$idProduct', '$idSize', '$idColor', '$quantity');");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createCartInfo();
?>