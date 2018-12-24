<?php
require_once("dbcontroller.php");

Class User {
	private $user = array();
	public function getAllUsers(){
		$query = "SELECT * FROM user";
		$dbcontroller = new DBController();
		$this->users = $dbcontroller->executeSelectQuery($query);
		return $this->users;
	}	
}
?>