<?php
  include_once '../dbConnect.php';
  function updateCart() {
    $db = new dbConnect();

    $idCart   = isset($_POST["idCart"]) ? $_POST["idCart"] : '';
    $idAccount = isset($_POST["idAccount"]) ? $_POST["idAccount"] : '';
    $isPaid = isset($_POST["isPaid"]) ? $_POST["isPaid"] : '';
    
    if (!empty($idCart) && !empty($idAccount) && !empty($isPaid)) {
      $query = "UPDATE cart SET isPaid = $isPaid WHERE idCart = $idCart and idAccount = $idAccount";
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
  updateCart();
?>