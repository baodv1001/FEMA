<?php
  include_once '../dbConnect.php';
  function createBill() {
    $db = new dbConnect();

    $idAccount = $_REQUEST['idAccount'];
    $invoiceDate = $_REQUEST['invoiceDate'];
    $status = $_REQUEST['status'];
    $idAddress = $_REQUEST['idAddress'];
    $totalMoney = $_REQUEST['totalMoney'];

    $result = mysqli_query($db->connect(), 
      "INSERT INTO bill (idAccount, invoiceDate, `status`, idAddress, totalMoney) 
      VALUES ('$idAccount',' $invoiceDate', '$status', '$idAddress', '$totalMoney')");

    header("Content-Type: Json");
    if($result) {
      $res = mysqli_query($db->connect(), 
      "SELECT MAX(idBill) as idBill FROM bill");
      $row = mysqli_fetch_assoc($res);
      echo json_encode($row['idBill']);
    } else {
      echo json_encode("0");
    }
  }
  createBill();
?>