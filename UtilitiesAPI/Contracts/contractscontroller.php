<?php
require_once("ContractRestHandler.php");
		
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
		$contractRestHandler = new ContractRestHandler();
		$contractRestHandler->getAllContracts();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
