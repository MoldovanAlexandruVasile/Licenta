<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertReport();
    }

    function insertReport(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $utility = (int)$_POST["utility"];
        $quantity = (int)$_POST["quantity"];
        $month = $_POST["month"];

        $query = "INSERT INTO report(user, utility, quantity, month) 
        VALUES ($user, $utility, $quantity, '$month'));";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>