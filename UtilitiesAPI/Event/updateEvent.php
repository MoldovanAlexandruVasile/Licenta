<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateEvent();
    }

    function updateEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $title = $_POST["title"];
        $details = $_POST["details"];
        $status = $_POST["status"];

        $query = "UPDATE event
                SET title = '$title', details = '$details', status = '$status'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>