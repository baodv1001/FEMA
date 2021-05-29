<?php
 include_once './dbConnect.php';
 function dispInfo(){
   $db = new dbConnect();
  // Mảng JSON
  $idProductCode = $_REQUEST['idProductCode'];
  $rating = $_REQUEST['rating'];
  $minPrice = $_REQUEST['minPrice'];
  $maxPrice = $_REQUEST['maxPrice'];
  $response = array();
  //$response["product"] = array();
  // Câu lệnh Select dùng để xem dữ liệu
  $result = mysqli_query($db->connect(),"SELECT * FROM product WHERE idProductCode='$idProductCode' and rating>='$rating' and price>='$minPrice' and price<= '$maxPrice'");
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