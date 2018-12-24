<?php
require_once("dbcontroller.php");

Class EventUser {
	private $user = array();
	public function getAllEventUsers(){
		$query = "SELECT * FROM event_user";
		$dbcontroller = new DBController();
		$this->eventUser = $dbcontroller->executeSelectQuery($query);
		return $this->eventUser;
	}	
}
?>