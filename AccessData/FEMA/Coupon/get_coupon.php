<?php
  include_once '../dbConnect.php';
  function getCoupon() {
    $db = new dbConnect();

    $code = $_REQUEST['code'];
    $response = array();
    $result = mysqli_query($db->connect(), 
      "SELECT * FROM Coupon WHERE code = '$code'");

    while($row = mysqli_fetch_assoc($result)) {
      $data = array();
      $data["idCoupon"] = $row["idCoupon"];
      $data["code"] = $row["code"];
      $data["value"] = $row["value"];
      
      array_push($response, $data);
    }
    header("Content-Type: Json");
    if(!empty($response[0]))
      echo json_encode($response[0]);
    else {
      $data = array();
      $data["idCoupon"] = "0";
      $data["code"] = "0";
      $data["value"] = "0";

      array_push($response, $data);
      echo json_encode($response[0]);
    }
  }
  getCoupon();
?>