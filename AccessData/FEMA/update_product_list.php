<?php
  include_once './dbConnect.php';
  function updateProduct() {
    $db = new dbConnect();

    $idProductList = $_POST['idProductList'];
    $idProductList = explode(",", $idProductList);

    $quantity = $_POST['quantity'];
    $quantity = explode(",", $quantity);

    for ($i = 0; $i < count($idProductList); $i++) {
      $q = $quantity[$i];
      $id = $idProductList[$i];
      $result = mysqli_query($db->connect(), 
        "UPDATE Product SET quantity = quantity + $q
        WHERE idProduct = $id");
    }

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateProduct();
?>