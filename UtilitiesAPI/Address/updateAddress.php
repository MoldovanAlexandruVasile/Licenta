<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        updateAddress();
    }

    function updateAddress(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = $_POST["ID"];
        $address = $_POST["address"];

        $query = "UPDATE address
                SET address = '$address'
                WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>