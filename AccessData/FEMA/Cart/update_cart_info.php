<?php
  include_once '../dbConnect.php';
  function updateCartInfo() {
    $db = new dbConnect();

    $idCart = $_REQUEST['idCart'];
    $idProduct = $_REQUEST['idProduct'];
    $quantity = $_REQUEST['quantity'];

    if (!empty($idCart) && !empty($idProduct) && !empty($quantity)) {
      $query = "UPDATE CartInfo SET quantity = '$quantity' WHERE idCart = $idCart and idProduct = $idProduct";
      $result = mysqli_query($db->connect(), $query);
      if ($result) {
        $resultData = true;
      } else {
        $resultData = false;
      }
    } else {
      $resultData = false;
    }

    header("Content-Type: Json");
    echo json_encode($resultData);
  }
  updateCartInfo();
?>