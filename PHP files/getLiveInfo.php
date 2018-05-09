<?php 
require "init.php";

$conductor_id = $_POST["conductor_id"];;

$mysql_qry = "SELECT * FROM Live WHERE conductor_id = '$conductor_id'";

$result = mysqli_query($con ,$mysql_qry);
$emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }

echo json_encode(array("live_info"=>$emparray));
?>

