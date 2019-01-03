<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateUser();
    }

    function updateUser(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $email = $_POST["email"];
        $password = $_POST["password"];
        $name = $_POST["name"];
        $address = $_POST["address"];

        $query = "UPDATE admin
                    SET password = '$password'
                    WHERE ID = '$ID' AND email = '$email' AND name = '$name'
                    AND address = '$address';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>