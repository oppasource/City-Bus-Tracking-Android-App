<?php
   require 'init.php';

   function distance($lat1, $lon1, $lat2, $lon2) {

		$theta = $lon1 - $lon2;
		$dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
		$dist = acos($dist);
		$dist = rad2deg($dist);
		$dist = $dist * 60 * 1.1515* 1.609344;

		return ($dist);
	}
		

   $lat =$_POST['latitude'];
   $lon = $_POST['longitude'];
   $nearest_stop = "something went wrong start the app again";
   
	$smallest_distance = 1000.0;

   $result = mysqli_query($con,"SELECT * FROM loc2stop");

   while($row = mysqli_fetch_assoc($result)) {
		$temp_dist =(float) distance($lat,$lon,$row['latitude'],$row['longitude']);

		if($temp_dist < $smallest_distance){
				$smallest_distance = $temp_dist;
				$nearest_stop = $row['bus_stop_name'];
		}
       
   }
	echo $nearest_stop;

   mysqli_close($con);
?>

