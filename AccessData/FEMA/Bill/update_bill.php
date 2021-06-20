<?php
  include_once '../dbConnect.php';
  function updateBill() {
    $db = new dbConnect();

    $idBill = $_REQUEST['idBill'];

    $result = mysqli_query($db->connect(), 
      "UPDATE bill SET status = 3 WHERE idBill = $idBill;");

    header("Content-Type: Json");
    echo json_encode($result);
  }
  updateBill();
?>