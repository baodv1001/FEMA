<?php
  include_once '../dbConnect.php';
  function updateCartInfo() {
    $db = new dbConnect();

    $idCart   = isset($_POST["idCart"]) ? $_POST["idCart"] : '';
    $idProduct = isset($_POST["idProduct"]) ? $_POST["idProduct"] : '';
    $quantity = isset($_POST["quantity"]) ? $_POST["quantity"] : '';
    
    if (!empty($idCart) && !empty($idProduct) && !empty($quantity)) {
      $query = "UPDATE CartInfo SET quantity = $quantity WHERE idCart = $idCart and idProduct = $idProduct";
      $result = mysqli_query($con, $query);
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