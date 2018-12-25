<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateEventUser();
    }

    function updateEventUser(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $user = (int)$_POST["user"];
        $event = (int)$_POST["event"];
        $status = $_POST["status"];

        $query = "UPDATE event_user
                SET user = '$user', event = '$event', status = '$status'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>