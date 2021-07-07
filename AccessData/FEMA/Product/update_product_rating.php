<?php
  include_once '../dbConnect.php';
  function updateProductRating() {
    $db = new dbConnect();

    $idProductList = $_REQUEST['idProductList'];
    $idProductList = explode(",", $idProductList);

    $rating = $_REQUEST['rating'];

    foreach ($idProductList as $idProduct) {
      $tmp = mysqli_fetch_assoc(mysqli_query($db->connect(), "SELECT rating FROM Product WHERE idProduct = $idProduct"));
      $rate = $tmp['rating'];
      $quantity = mysqli_num_rows(mysqli_query($db->connect(), "SELECT BillInfo.idBill FROM BillInfo JOIN Bill ON BillInfo.idBill = Bill.idBill WHERE idProduct = $idProduct GROUP BY BillInfo.idBill"));
      $newRate = ($rate * ($quantity - 1) + $rating) / ($quantity);
      
      $result = mysqli_query($db->connect(), 
        "UPDATE Product SET rating = $newRate WHERE idProduct = $idProduct");
    }

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateProductRating();
?>