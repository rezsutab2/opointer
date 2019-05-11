<?php
require_once('config.php');

$userid=$_POST["userid"];
$user2name=$_POST["user2name"];
$stmt = mysqli_prepare($connect, "INSERT INTO friendship (user1_id,user2_id,isapproved) VALUES ((Select u.user_id from user u where u.username LIKE ?),?,0)");
$stmt->bind_param('si',$user2name,$userid);
$stmt->execute();

?>