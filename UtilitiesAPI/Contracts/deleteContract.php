<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteContract();
    }

    function deleteContract(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = (int)$_POST["ID"];
        $query = "DELETE FROM contracts WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>