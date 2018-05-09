<?php 
require "init.php";
$route_number = $_POST["route_number"];
$mysql_qry = "SELECT bus_stop_name, stop_order FROM Routes WHERE bus_number LIKE '$route_number';";
$result = mysqli_query($con ,$mysql_qry);

$emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }

echo json_encode(array("response"=>$emparray));
 
?>
