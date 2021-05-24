<?php
  include_once './dbConnect.php';
  function getProductList(){
    $db = new dbConnect();

    $idProductList = $_POST['idProductList'];
    $idProductList = explode(",", $idProductList);
    $response = array();
 
    foreach ($idProductList as $idProduct) {
      $result = mysqli_query($db->connect(), 
      "SELECT * FROM product WHERE idProduct = $idProduct");

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

        array_push($response, $data);
      }
    }

    header("Content-Type: Json");
    echo json_encode($response);
  }
  getProductList();
?>