<?php
 include_once '../dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
  // Mảng JSON
  $response = array();
  //$response["product"] = array();
  // Câu lệnh Select dùng để xem dữ liệu
  $result = mysqli_query($db->connect(),"SELECT * FROM size;");
  //Đọc dữ liệu từ MySQL
  while($row = mysqli_fetch_assoc($result)){
    $t = array();
    $t["idSize"] = $row["idSize"];
    $t["name"] = $row["name"];
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