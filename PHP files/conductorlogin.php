<?php 
require "init.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$mysql_qry = "SELECT * FROM Conductors WHERE username LIKE '$user_name' AND password LIKE '$user_pass';";
$result = mysqli_query($con ,$mysql_qry);

if(mysqli_num_rows($result) > 0) {
   $row = mysqli_fetch_assoc($result);
   echo $row["conductor_id"];
}
else {
echo "login not success";
}
 
?>
