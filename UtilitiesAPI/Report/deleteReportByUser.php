<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        deleteReport();
    }

    function deleteReport(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $user = (int)$_POST["user"];
        $query = "DELETE FROM report WHERE user = '$user';";

        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>