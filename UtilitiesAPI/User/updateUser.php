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
        $apartment = (int)$_POST["apartment"];

        $query = "UPDATE user
                SET email = '$email', password = '$password', name = '$name', address = '$address', apartment = '$apartment'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>