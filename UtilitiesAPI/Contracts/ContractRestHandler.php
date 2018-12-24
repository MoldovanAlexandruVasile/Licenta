<?php
require_once("SimpleRest.php");
require_once("Contract.php");
		
class ContractRestHandler extends SimpleRest {

	function getAllContracts() {	

		$contract = new Contract();
		$rawData = $contract->getAllContracts();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No contract found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["contract"] = $rawData;

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