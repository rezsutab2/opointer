<?php
require_once('config.php');

$user2id=$_POST["user2_id"];
$userid=$_POST["user_id"];

$stmt = mysqli_prepare($connect, "DELETE FROM friendship WHERE user1_id=? AND user2_id=?");
$stmt->bind_param('ii',$userid,$user2id);
$stmt->execute();

?>