<?php
require_once("EventUserRestHandler.php");
		
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
		$eventUserRestHandler = new EventUserRestHandler();
		$eventUserRestHandler->getAllEventUsers();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
