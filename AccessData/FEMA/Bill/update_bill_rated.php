<?php
  include_once '../dbConnect.php';
  function updateBillRated() {
    $db = new dbConnect();

    $idBill = $_REQUEST['idBill'];

    $result = mysqli_query($db->connect(), 
      "UPDATE bill SET isRated = 1 WHERE idBill = $idBill;");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateBillRated();
?>