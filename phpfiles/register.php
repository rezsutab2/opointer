<?php
//Meghívom a config.php file-t a MySQL kapcsolat létrehozása érdekében.
require_once('config.php');

//A változók POST metódus álltal értéket vesznek fel.
$username=$_POST["username"];
$password=$_POST["password"];
$password=password_hash($password,PASSWORD_DEFAULT);
$name=$_POST["name"];
$birthdate=$_POST["birthdate"];

//Változóban tárolom a SQL parancsot. A bemenő értékeket a változókkal határozom meg.
$sqlquery="INSERT INTO user (username,password,name,birthdate) values('$username','$password','$name','$birthdate');";

//A parancs lefuttatához megadom a MySQL kapcsolatot és magát a parancsot is.
//Ha ezt a parancsot nem tudja véghezvinni valamiért, akkor kiíratom a hiba okát.
if(!mysqli_query($connect,$sqlquery)){
	echo 'A beszúrás nem történt meg. A hiba oka: '.mysqli_error($connect);
}

?>