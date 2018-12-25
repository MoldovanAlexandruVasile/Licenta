<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertAddress();
    }

    function insertAddress(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $event = (int)$_POST["event"];
        $status = $_POST["status"];

        $query = "INSERT INTO event_user(user, event, status) VALUES ('$user', '$event', '$status');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>