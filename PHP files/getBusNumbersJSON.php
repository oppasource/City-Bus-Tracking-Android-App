<?php 
require "init.php";

$mysql_qry = "SELECT DISTINCT bus_number FROM Routes";

$result = mysqli_query($con ,$mysql_qry);

$emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }

echo json_encode(array("bus_numbers"=>$emparray));
?>

