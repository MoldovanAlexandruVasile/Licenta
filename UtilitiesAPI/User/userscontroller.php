<?php
require_once("UserRestHandler.php");
		
$view = "";
if(isset($_GET["view"]))
	$view = $_GET["view"];
/*
controls the RESTful services
URL mapping
*/
switch($view){

	case "all":
		// to handle REST Url /mobile/list/
		$userRestHandler = new UserRestHandler();
		$userRestHandler->getAllUsers();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
