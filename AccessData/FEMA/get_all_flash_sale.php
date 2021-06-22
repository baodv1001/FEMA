<?php
 include_once './dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
  // Mảng JSON
  $response = array();
  //$response["product"] = array();
  // Câu lệnh Select dùng để xem dữ liệu
  $result = mysqli_query($db->connect(),"SELECT * FROM product where discount > '0';");
  //Đọc dữ liệu từ MySQL
  while($row = mysqli_fetch_assoc($result)){
    $t = array();
    $t["idProduct"] = $row["idProduct"];
    $t["name"] = $row["name"];
    $t["idProductCode"] = $row["idProductCode"];
    $t["price"] = $row["price"];
    $t["quantity"] = $row["quantity"];
    $t["unit"] = $row["unit"];
    $t["imageFile"] = $row["imageFile"];
    $t["discount"] = $row["discount"];
    $t["rating"] = $row["rating"];
    $t["description"] = $row["description"];
    $t["isDeleted"] = $row["isDeleted"];
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