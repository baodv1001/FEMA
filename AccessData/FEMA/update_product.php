<?php
  include_once './dbConnect.php';
  function updateProduct() {
    $db = new dbConnect();

    $idProduct = $_REQUEST['idProduct'];
    $quantity = $_REQUEST['quantity'];

    $result = mysqli_query($db->connect(), 
    "UPDATE Product SET quantity = quantity - $quantity 
    WHERE idProduct = $idProduct");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateProduct();
?>