<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateEvent();
    }

    function updateEvent(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $title = $_POST["title"];
        $details = $_POST["details"];
        $date = $_POST["date"];
        $hour = $_POST["hour"];
        $minute = $_POST["minute"];
        $address = $_POST["address"];

        $query = "UPDATE event
                  SET title = '$title', details = '$details',
                      date = '$date', hour = '$hour', minute = '$minute', 
                      address = '$address'
                  WHERE ID = '$ID';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>