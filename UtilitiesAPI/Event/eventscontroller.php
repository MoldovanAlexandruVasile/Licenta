<?php
require_once("EventRestHandler.php");
		
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
		$eventRestHandler = new EventRestHandler();
		$eventRestHandler->getAllEvents();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
