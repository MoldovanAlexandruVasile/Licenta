<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateCharge();
    }

    function updateCharge(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = (int)$_POST["ID"];
        $garage = (int)$_POST["garage"];
        $reparations = (int)$_POST["reparations"];
        $cleaning = (int)$_POST["cleaning"];
        $month = $_POST["month"];
        $address = $_POST["address"];

        $query = "UPDATE charges
                SET garage = '$garage', reparations = '$reparations', cleaning = '$cleaning'
                WHERE ID = $ID AND month = '$month' AND address = '$address';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>