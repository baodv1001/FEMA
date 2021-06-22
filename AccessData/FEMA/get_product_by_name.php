<?php
 include_once './dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
  // Mảng JSON
  $name = $_REQUEST['name'];
  $idProductCode = $_REQUEST['idProductCode'];
  $response = array();
  //$response["product"] = array();
  // Câu lệnh Select dùng để xem dữ liệu
  if($idProductCode!='0')
  {
    $result = mysqli_query($db->connect(),"SELECT * FROM product WHERE name like '%$name%' and idProductCode='$idProductCode'");
  }
  else
  {
    $result = mysqli_query($db->connect(),"SELECT * FROM product WHERE name like '%$name%' and discount>'0'");
  }
  //Đọc dữ liệu từ MySQL
  while($row = mysqli_fetch_assoc($result)){
    $data = array();
    $data["idProduct"] = $row["idProduct"];
    $data["name"] = $row["name"];
    $data["idProductCode"] = $row["idProductCode"];
    $data["price"] = $row["price"];
    $data["quantity"] = $row["quantity"];
    $data["unit"] = $row["unit"];
    $data["imageFile"] = $row["imageFile"];
    $data["discount"] = $row["discount"];
    $data["rating"] = $row["rating"];
    $data["description"] = $row["description"];
    $data["isDeleted"] = $row["isDeleted"];
    // Mảng JSON
    array_push($response, $data);
 }
 // Thiết lập header là JSON
 header("Content-Type: Json");
 // Hiển thị kết quả
 echo json_encode($response);
}
dispInfo();
?>