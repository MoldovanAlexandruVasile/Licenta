<?php
require_once("ReportRestHandler.php");
		
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
		$reportRestHandler = new ReportRestHandler();
		$reportRestHandler->getAllReports();
		break;

	case "" :
		//404 - not found;
		break;
}
?>
