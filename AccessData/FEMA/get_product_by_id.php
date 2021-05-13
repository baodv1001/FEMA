<?php
  include_once './dbConnect.php';
  function dispInfo(){
    $db = new dbConnect();
    // Mảng JSON
    $idProduct = $_REQUEST['idProduct'];
    $response = array();
    // Câu lệnh Select dùng để xem dữ liệu
    $result = mysqli_query($db->connect(),"SELECT * FROM product WHERE idProduct=$idProduct");
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