<?php
require_once("SimpleRest.php");
require_once("Report.php");
		
class ReportRestHandler extends SimpleRest {

	function getAllReports() {	

		$report = new Report();
		$rawData = $report->getAllReports();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No reports found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["report"] = $rawData;

		if(strpos($requestContentType,'application/json') !== false){
			$response = $this->encodeJson($result);
			echo $response;
		}
	}
	
	public function encodeJson($responseData) {
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}
}
?>