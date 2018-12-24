<?php
require_once("SimpleRest.php");
require_once("Event.php");
		
class EventRestHandler extends SimpleRest {

	function getAllEvents() {	

		$event = new Event();
		$rawData = $event->getAllEvents();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No events found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["event"] = $rawData;

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