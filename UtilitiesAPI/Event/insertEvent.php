<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertEvent();
    }

    function insertEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $title = $_POST["title"];
        $details = $_POST["details"];
        $status = $_POST["status"];

        $query = "INSERT INTO event(title, details, status) VALUES ('$title', '$details', '$status');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>