<?php
  include_once './dbConnect.php';
  function updateProductRating() {
    $db = new dbConnect();

    $idProductList = $_POST['idProductList'];
    $idProductList = explode(",", $idProductList);

    $rating = $_POST['rating'];

    foreach ($idProductList as $idProduct) {
      $tmp = mysqli_fetch_assoc(mysqli_query($db->connect(), "SELECT rating FROM Product WHERE idProduct = $idProduct"));
      $rate = $tmp['rating'];
      $quantity = mysqli_num_rows(mysqli_query($db->connect(), "SELECT idBill FROM BillInfo WHERE idProduct = $idProduct GROUP BY idBill"));
      $newRate = ($rate * $quantity + $rating) / ($quantity + 1);
      
      $result = mysqli_query($db->connect(), 
        "UPDATE Product SET rating = $newRate WHERE idProduct = $idProduct");
    }

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateProductRating();
?>