<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateAdmin();
    }

    function updateAdmin(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $email = $_POST["email"];
        $password = $_POST["password"];
        $name = $_POST["name"];


        $query = "UPDATE admin
                SET email = '$email', password = '$password', name = '$name'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>