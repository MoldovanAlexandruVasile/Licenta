<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertContract();
    }

    function insertContract(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $water = $_POST["water"];
        $gas = $_POST["gas"];
        $electricity = $_POST["electricity"];

        $query = "INSERT INTO contracts (user, water, gas, electricity) VALUES ('$user', '$water', '$gas', '$electricity');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>