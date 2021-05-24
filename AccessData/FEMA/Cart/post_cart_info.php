<?php
  include_once '../dbConnect.php';
  function createCartInfo() {
    $db = new dbConnect();

    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];
    $quantity = $_REQUEST['quantity'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO cartinfo (idCart, idProduct, quantity) 
      VALUES ($idCart, $idProduct, $quantity);");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createCartInfo();
?>