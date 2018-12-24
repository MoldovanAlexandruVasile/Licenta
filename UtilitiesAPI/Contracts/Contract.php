<?php
require_once("dbcontroller.php");

Class Contract {
	private $user = array();
	public function getAllContracts(){
		$query = "SELECT * FROM contracts";
		$dbcontroller = new DBController();
		$this->contracts = $dbcontroller->executeSelectQuery($query);
		return $this->contracts;
	}	
}
?>