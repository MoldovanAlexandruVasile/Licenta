<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertEvent();
    }

    function insertEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $title = $_POST["title"];
        $details = $_POST["details"];

        $query = "INSERT INTO event(title, details) VALUES ('$title', '$details'));";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>