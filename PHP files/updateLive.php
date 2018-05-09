<?php 
require "init.php";

$lat = $_POST["lat"];
$longi = $_POST["long"];
$conductor_id = $_POST["conductor_id"];

//returns distance between 2 points given its latitude and longitude in kilometers
function distance($lat1, $lon1, $lat2, $lon2) {
	$theta = $lon1 - $lon2;
	$dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
	$dist = acos($dist);
	$dist = rad2deg($dist);
	$dist = $dist * 60 * 1.1515* 1.609344;
	return ($dist);
}


//get lat and longi
$query = "SELECT * FROM next_distance WHERE conductor_id='$conductor_id';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$c_distance = $row['distance_to_next'];
$next_lat = $row['next_stop_lat'];
$next_longi = $row['next_stop_long'];
$distance = distance($lat, $longi, $next_lat, $next_longi);

//setting up next_stop_name to echo
$query = "SELECT next_stop FROM Live WHERE conductor_id='$conductor_id';";
$result = mysqli_query($con ,$query);
$row = mysqli_fetch_assoc($result);
$next_stop_name = $row['next_stop'];



if(is_null($c_distance)){
$c_distance = 1000;
}

if (abs((float)$distance - (float)$c_distance) > 0.08){
	if((float)$distance < (float)$c_distance){
		//going towards next stop

		$query = "UPDATE next_distance SET distance_to_next = '$distance' WHERE next_distance.conductor_id = '$conductor_id';";
		$result = mysqli_query($con ,$query);
		//$row = mysqli_fetch_assoc($result);

	}else{
		//going away from next stop i.e. next stop has been serviced and going ahead (needs to update everything)

		//get nxt_ord, dir, r_num
		$query = "SELECT next_stop_order,direction,route_number FROM Live WHERE conductor_id='$conductor_id';";
		$result = mysqli_query($con ,$query);
		$row = mysqli_fetch_assoc($result);
		$next_order = $row['next_stop_order'];
		$direction = $row['direction'];
		$route_number = $row['route_number'];
		$next_order = (int)$next_order + (int)$direction;

		//get nxt_stop_name
		$query = "SELECT bus_stop_name FROM Routes WHERE bus_number='$route_number' AND stop_order='$next_order';";
		$result = mysqli_query($con ,$query);
		$row = mysqli_fetch_assoc($result);
		$next_stop_name = $row['bus_stop_name'];

		//get lat and long of nxt_stop
		$query = "SELECT latitude,longitude FROM loc2stop WHERE bus_stop_name='$next_stop_name';";
		$result = mysqli_query($con ,$query);
		$row = mysqli_fetch_assoc($result);
		$next_lat = $row['latitude'];
		$next_longi = $row['longitude'];

		//update live-  next_order, next_name
		$query = "UPDATE Live SET next_stop_order = '$next_order', next_stop = '$next_stop_name' WHERE conductor_id = '$conductor_id';";
		$result = mysqli_query($con ,$query);
		//$row = mysqli_fetch_assoc($result);

		//updatenext_distance-  distance_to_next, next_lat, next_long
		$query = "UPDATE next_distance SET distance_to_next = '1000', next_stop_lat = '$next_lat', next_stop_long = '$next_longi' WHERE conductor_id = '$conductor_id';";
		$result = mysqli_query($con ,$query);
		//$row = mysqli_fetch_assoc($result);	
	}
}

echo($next_stop_name);
 
?>

