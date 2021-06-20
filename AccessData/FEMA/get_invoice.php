<?php
 include_once './dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
  // Mảng JSON
  $idAccount = $_REQUEST['idAccount'];
  $response = array();
  //$response["product"] = array();
  // Câu lệnh Select dùng để xem dữ liệu
  $result = mysqli_query($db->connect(),"SELECT * FROM bill WHERE idAccount='$idAccount';");
  //Đọc dữ liệu từ MySQL
  while($row = mysqli_fetch_assoc($result)){
    $t = array();
    $t["id"] = $row["idBill"];
    $t["idAccount"] = $row["idAccount"];
    $t["invoiceDate"] = $row["invoiceDate"];
    $t["status"] = $row["status"];
    $t["idAddress"] = $row["idAddress"];
    $t["totalMoney"] = $row["totalMoney"];
    $t["isRated"] = $row["isRated"];
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