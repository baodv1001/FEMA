<?php
  include_once './dbConnect.php';
  function dispInfo(){
    $db = new dbConnect();
    // Mảng JSON
    $response = array();
    $idAccount = $_REQUEST['idAccount'];
    // Câu lệnh Select dùng để xem dữ liệu
    $result = mysqli_query($db->connect(),"SELECT * FROM addressinfo WHERE idAccount = '$idAccount';");
    //Đọc dữ liệu từ MySQL
    while($row = mysqli_fetch_assoc($result)){
      $t = array();
      $t["idAccount"] = $row["idAccount"];
      $t["name"] = $row["name"];
      $t["address"] = $row["address"];
      $t["phoneNumber"] = $row["phoneNumber"];
      // Mảng JSON
      array_push($response, $t);
    }
  // Thiết lập header là JSON
    header("Content-Type: Json");
  // Hiển thị kết quả
    echo json_encode($response);
  }
  dispInfo();
?>