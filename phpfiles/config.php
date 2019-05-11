<?php

//Csatlakozáshoz szükséges adatok
$host="localhost";
$dbname="id9120620_opointer";
$dbuser="id9120620_rezsutab";
$dbpass="kdV7ju2ZYezpb9D";

//Az adatok felhasználásával létrehozom a kapcsolatot
$connect=mysqli_connect($host,$dbuser,$dbpass,$dbname);

//Ha nem sikerült csatlakozni hibaüzenetet jelenítek meg
if(!$connect){
	echo "Nem sikerült csatlakozni: ".mysqli_connect_error();
}

?>