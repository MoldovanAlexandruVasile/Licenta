<?php
require_once("ChargeRestHandler.php");
		
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
		$chargeRestHandler = new ChargeRestHandler();
		$chargeRestHandler->getAllCharges();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
