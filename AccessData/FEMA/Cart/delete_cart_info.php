<?php
  include_once '../dbConnect.php';
  function deleteCartInfo() {
    $db = new dbConnect();

    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];

    $result = mysqli_query($db->connect(), 
      "DELETE FROM cartinfo 
      WHERE idCart = $idCart and idProduct = $idProduct;");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  deleteCartInfo();
?>