<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteContract();
    }

    function deleteContract(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $query = "DELETE FROM contracts WHERE user = '$user';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>