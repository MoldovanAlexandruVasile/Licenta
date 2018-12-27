<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateReport();
    }

    function updateReport(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $utility = $_POST["utility"];
        $quantity = (int)$_POST["quantity"];
        $month = $_POST["month"];
        $date = $_POST["date"];

        $query = "UPDATE report
                SET quantity = '$quantity', date = '$date'
                WHERE user = '$user' AND utility = '$utility' AND month = '$month'";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>