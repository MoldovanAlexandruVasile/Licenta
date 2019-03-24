<?php
require_once("SimpleRest.php");
require_once("Charge.php");
		
class ChargeRestHandler extends SimpleRest {

	function getAllCharges() {	

		$charge = new Charge();
		$rawData = $charge->getAllCharges();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No charge found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["charges"] = $rawData;

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