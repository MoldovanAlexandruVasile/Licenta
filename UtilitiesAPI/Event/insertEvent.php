<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertEvent();
    }

    function insertEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $title = $_POST["title"];
        $details = $_POST["details"];
        $address = $_POST["address"];

        $query = "INSERT INTO event(title, details, address) VALUES ('$title', '$details', '$address');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>