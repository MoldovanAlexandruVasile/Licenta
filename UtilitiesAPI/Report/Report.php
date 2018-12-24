<?php
require_once("dbcontroller.php");

Class Report {
	private $user = array();
	public function getAllReports(){
		$query = "SELECT * FROM report";
		$dbcontroller = new DBController();
		$this->report = $dbcontroller->executeSelectQuery($query);
		return $this->report;
	}	
}
?>