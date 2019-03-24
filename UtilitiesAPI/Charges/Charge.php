<?php
require_once("dbcontroller.php");

Class Charge {
	private $user = array();
	public function getAllCharges(){
		$query = "SELECT * FROM charges";
		$dbcontroller = new DBController();
		$this->charges = $dbcontroller->executeSelectQuery($query);
		return $this->charges;
	}	
}
?>