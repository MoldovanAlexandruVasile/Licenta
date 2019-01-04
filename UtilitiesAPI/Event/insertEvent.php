<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertEvent();
    }

    function insertEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $title = $_POST["title"];
        $details = $_POST["details"];
        $date = $_POST["date"];
        $hour = $_POST["hour"];
        $minute = $_POST["minute"];
        $address = $_POST["address"];

        $query = "INSERT INTO event(title, details, date, hour, minute, address)
                  VALUES ('$title', '$details', '$date', '$hour', '$minute', '$address');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>