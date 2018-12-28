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
        $garage = $_POST["garage"];

        $query = "INSERT INTO contracts (user, water, gas, electricity, garage)
                  VALUES ('$user', '$water', '$gas', '$electricity', '$garage');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>