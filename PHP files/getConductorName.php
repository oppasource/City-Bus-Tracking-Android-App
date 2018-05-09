<?php 
require "init.php";
$id = $_POST["id"];

$mysql_qry = "SELECT name FROM Conductors WHERE conductor_id LIKE '$id'";
$result = mysqli_query($con ,$mysql_qry);

if($result) {
    $row = mysqli_fetch_assoc($result);
    echo $row['name'];
    
}
else {
echo "error";
}
 
?>

