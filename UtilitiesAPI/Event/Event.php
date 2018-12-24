<?php
require_once("dbcontroller.php");

Class Event {
	private $user = array();
	public function getAllEvents(){
		$query = "SELECT * FROM event";
		$dbcontroller = new DBController();
		$this->event = $dbcontroller->executeSelectQuery($query);
		return $this->event;
	}	
}
?>