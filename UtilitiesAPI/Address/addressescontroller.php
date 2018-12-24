<?php
require_once("AddressRestHandler.php");
		
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
		$addressRestHandler = new AddressRestHandler();
		$addressRestHandler->getAllAddresses();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
