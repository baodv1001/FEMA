<?php
  include_once '../dbConnect.php';
  function createBill() {
    $db = new dbConnect();

    // $idBill = $_REQUEST['idBill'];
    // $idAccount = $_REQUEST['idAccount'];
    // $invoiceDate = $_REQUEST['invoiceDate'];
    // $status = $_REQUEST['status'];
    // $totalMoney = $_REQUEST['totalMoney'];

    $idBill = 3;
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
  createBill();
?>