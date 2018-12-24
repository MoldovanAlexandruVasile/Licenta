<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteUser();
    }

    function deleteUser(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = (int)$_POST["ID"];
        $query = "DELETE FROM user WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>