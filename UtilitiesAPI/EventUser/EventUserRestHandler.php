<?php
require_once("SimpleRest.php");
require_once("EventUser.php");
		
class EventUserRestHandler extends SimpleRest {

	function getAllEventUsers() {	

		$eventUser = new EventUser();
		$rawData = $eventUser->getAllEventUsers();
		
		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No event_user found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["event_user"] = $rawData;

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