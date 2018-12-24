<?php
require_once("SimpleRest.php");
require_once("Address.php");
		
class AddressRestHandler extends SimpleRest {

	function getAllAddresses() {	

		$address = new Address();
		$rawData = $address->getAllAddresses();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No address found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["address"] = $rawData;

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