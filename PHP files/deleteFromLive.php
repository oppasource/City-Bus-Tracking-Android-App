<?php 
require "init.php";
$conductor_id = $_POST["conductor_id"];

$mysql_qry = "DELETE FROM Live WHERE conductor_id = '$conductor_id'";
mysqli_query($con, $mysql_qry);

$mysql_qry = "DELETE FROM next_distance WHERE conductor_id = '$conductor_id';";
mysqli_query($con, $mysql_qry);

 
?>


