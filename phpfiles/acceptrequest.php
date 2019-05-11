<?php
require_once('config.php');

$user2id=$_POST["user2_id"];
$stmt = mysqli_prepare($connect, "UPDATE friendship SET isapproved=1 WHERE user2_id=?");
$stmt->bind_param('i',$user2id);
$stmt->execute();

?>