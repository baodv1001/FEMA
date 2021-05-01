<?php
class dbConnect {
 private $conn;
 // Phương thức khởi tạo
 function __construct() {
   // Kết nối đến database
   $this->connect();
 }
 function __destruct() {
  // Đóng kết nối
  $this->close();
 }
 /**
 * Thiết lập kết nối đến CSDL
 *
 */
 function connect() {
   include_once dirname(__FILE__) . './config.php';
  // Kết nối đến MySQL
  $this->conn = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD) or die(mysqli_error());
  // Chọn cơ sở dữ liệu
  mysqli_select_db($this->conn,DB_NAME) or die(mysqli_error());
  // Trả về đối tượng connection
  return $this->conn;
 }
 /**
 * Đóng kết nối
 */
 function close() {
   // Đóng kết nối CSDL
   mysqli_close($this->conn);
 }
}
?>