<?php
require_once("dbcontroller.php");

Class Admin {
	private $user = array();
	public function getAllAdmins(){
		$query = "SELECT * FROM admin";
		$dbcontroller = new DBController();
		$this->admins = $dbcontroller->executeSelectQuery($query);
		return $this->admins;
	}	
}
?>