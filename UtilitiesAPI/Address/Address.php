<?php
require_once("dbcontroller.php");

Class Address {
	private $user = array();
	public function getAllAddresses(){
		$query = "SELECT * FROM address";
		$dbcontroller = new DBController();
		$this->address = $dbcontroller->executeSelectQuery($query);
		return $this->address;
	}	
}
?>