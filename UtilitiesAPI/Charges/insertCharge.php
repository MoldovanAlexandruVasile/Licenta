<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertCharge();
    }

    function insertCharge(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $garage = (int) $_POST["garage"];
        $reparations = (int) $_POST["reparations"];
        $cleaning = (int) $_POST["cleaning"];
        $month = $_POST["month"];
        $address =  $_POST["address"];

        $query = "INSERT INTO charges (garage, reparations, cleaning, month, address) 
                    VALUES ('$garage', '$reparations', '$cleaning', '$month', '$address');";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>