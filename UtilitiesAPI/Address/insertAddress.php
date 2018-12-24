<?php 
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        insertAddress();
    }

    function insertAddress(){
        $connect = mysqli_connect('localhost', 'root', '', 'utilities');
        $address = $_POST["address"];

        $query = "INSERT INTO address(address) VALUES ('$address'));";
        mysqli_query($connect, $query) or die (mysqli_error($connect));
        mysqli_close($connect); 
    }
?>