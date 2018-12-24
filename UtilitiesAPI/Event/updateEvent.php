<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateEvent();
    }

    function updateEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $title = $_POST["title"];
        $details = $_POST["details"];

        $query = "UPDATE event
                SET title = '$title', details = '$details'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>