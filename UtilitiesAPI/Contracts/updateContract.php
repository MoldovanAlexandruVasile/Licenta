<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateContract();
    }

    function updateContract(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $user = (int)$_POST["user"];
        $water = $_POST["water"];
        $gas = $_POST["gas"];
        $electricity = $_POST["electricity"];

        $query = "UPDATE contracts
                SET user = $user, water = '$water', gas = '$gas', electricity = '$electricity'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>