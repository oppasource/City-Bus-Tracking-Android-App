<?php 
require "init.php";
$id = $_POST["id"];
$route_number = $_POST["route_number"];
$seletedstop = $_POST["seletedstop"];
$v_num = $_POST["v_num"];
$end_point = $_POST["end_point"];
$start_point = $seletedstop;

//next_order and direction
$query = "SELECT stop_order FROM Routes WHERE bus_number = '$route_number' and bus_stop_name = '$seletedstop';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$order = (int)$row['stop_order'];

if($order == 1){
$direction = 1;
}else{
$direction = -1;
}
$next_order = $order + $direction;


//conductor name
$query = "SELECT name FROM Conductors WHERE conductor_id='$id';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$c_name = $row['name'];


//next bus stop name
$query = "SELECT bus_stop_name FROM Routes WHERE bus_number='$route_number' AND stop_order='$next_order';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$next_stop_name = $row['bus_stop_name'];
 

$mysql_qry = "INSERT INTO Live ( conductor_id, conductor_name, route_number, vehicle_number, next_stop_order, direction,next_stop, start_stop, end_stop) VALUES ('$id','$c_name', '$route_number', '$v_num', '$next_order', '$direction','$next_stop_name', '$start_point', '$end_point');";

mysqli_query($con, $mysql_qry);

//geting next_lat and next_long
$query = "SELECT latitude,longitude FROM loc2stop WHERE bus_stop_name='$next_stop_name';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$next_lat = $row['latitude'];
$next_longi = $row['longitude'];

//inserting data into next_stop_distance
$query = "INSERT INTO next_distance (conductor_id, distance_to_next, next_stop_lat, next_stop_long) VALUES ('$id', '1000', '$next_lat', '$next_longi');";
mysqli_query($con, $query);
?>


