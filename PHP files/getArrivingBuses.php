<?php 
require "init.php";

$stop = $_POST["stop_name"];

//getting bus_number that go through a particular stop
$query = "SELECT DISTINCT bus_number FROM Routes WHERE bus_stop_name = '$stop';";
$result = mysqli_query($con ,$query);

$emparray = array();
while($row =mysqli_fetch_assoc($result))
{
	//get stop_order of each bus_number for given stop
	$num = $row['bus_number'];
    $query = "SELECT stop_order FROM Routes WHERE bus_stop_name = '$stop' AND bus_number = '$num';";
    $inner = mysqli_query($con ,$query);
	$inner_row = mysqli_fetch_assoc($inner);
	$order = (int)$inner_row['stop_order'];

	//getting bus numbers that are live
	$query = "SELECT route_number,next_stop,end_stop,direction FROM Live WHERE direction ='1' AND next_stop_order <= '$order' AND route_number = '$num'  UNION ALL SELECT route_number,next_stop,end_stop,direction FROM Live WHERE direction ='-1' AND next_stop_order >= '$order' AND route_number = '$num';  ";
	$final = mysqli_query($con ,$query);

	if(!empty($final)){ 
			while($final_row = mysqli_fetch_assoc($final)){
			$emparray[] = $final_row;
		}

	}

	
}
echo json_encode(array("live_feed"=>$emparray));
?>


