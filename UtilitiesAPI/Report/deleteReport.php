<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteReport();
    }

    function deleteReport(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $ID = (int)$_POST["ID"];
        $query = "DELETE FROM report WHERE ID = $ID;";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>