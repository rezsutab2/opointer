<?php
require_once('config.php');

$latitude=$_POST["latitude"];
$longitude=$_POST["longitude"];
$message=$_POST["message"];
$userid=$_POST["user_id"];

$stmt = mysqli_prepare($connect, "INSERT INTO location (latitude,longitude) VALUES (?,?)");
$stmt->bind_param('dd',$latitude,$longitude);
$stmt->execute();

$stmt2= mysqli_prepare($connect,"INSERT INTO event (date,message,user_id,location_id) VALUES ((SELECT UTC_TIMESTAMP),?,?,(SELECT MAX(location_id) FROM location))");
$stmt2->bind_param('si',$message,$userid);
$stmt2->execute();

?>