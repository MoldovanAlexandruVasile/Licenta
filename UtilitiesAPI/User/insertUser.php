<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertUser();
    }

    function insertUser(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $email = $_POST["email"];
        $password = $_POST["password"];
        $name = $_POST["name"];
        $address = $_POST["address"];
        $apartment = $_POST["apartment"];

        $query = "INSERT INTO user(email, password, name, address, apartament) VALUES
                            ('$email', '$password', '$name', '$address', '$apartment'));";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>