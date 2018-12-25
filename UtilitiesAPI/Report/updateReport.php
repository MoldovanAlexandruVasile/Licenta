<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateReport();
    }

    function updateReport(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $user = (int)$_POST["user"];
        $utility = (int)$_POST["utility"];
        $quantity = (int)$_POST["quantity"];
        $month = $_POST["month"];

        $query = "UPDATE report
                SET user = '$user', utility = '$utility', quantity = '$quantity', month = '$month'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>