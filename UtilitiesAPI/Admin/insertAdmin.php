<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertAdmin();
    }

    function insertAdmin(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $email = $_POST["email"];
        $password = $_POST["password"];
        $name = $_POST["name"];

        $query = "INSERT INTO admin (email, password, name) 
                    VALUES ('$email', '$password', '$name');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>