<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateContract();
    }

    function updateContract(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $water = $_POST["water"];
        $gas = $_POST["gas"];
        $electricity = $_POST["electricity"];
        $garage = $_POST["garage"];

        $query = "UPDATE contracts
                SET water = '$water', gas = '$gas', electricity = '$electricity', garage = '$garage'
                WHERE user = '$user';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>