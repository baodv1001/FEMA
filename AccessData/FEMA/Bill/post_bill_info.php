<?php
  include_once '../dbConnect.php';
  function createBillInfo() {
    $db = new dbConnect();

    $idBill = $_REQUEST['idBill'];
    $idProduct = $_REQUEST['idProduct'];
    $quantity = $_REQUEST['quantity'];
    $status = $_REQUEST['status'];
    $totalMoney = $_REQUEST['totalMoney'];

    $idBill = 2;
    $idAccount = 1;
    $invoiceDate = '2021-05-13';
    $status = 'ABC';
    $totalMoney = 44;

    $result = mysqli_query($db->connect(), 
      "INSERT INTO bill (idBill, idAccount, invoiceDate, status, totalMoney) 
      VALUES ($idBill, $idAccount, $invoiceDate, $status, $totalMoney);");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  createBillInfo();
?>