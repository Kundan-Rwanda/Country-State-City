<?php
/*
 Created By Kundan on 28/10/2018
 Email: kundan.kumar011@gmail.com
*/
require_once('db_connection.php');

//Assign Varibale fetching from GET Method
$state_id=$_GET['state_id'];

$sql = "SELECT * FROM City WHERE state_id = $state_id";
$res = mysqli_query($con,$sql);
$result = array();
while($row = mysqli_fetch_array($res)){
 array_push($result,array(
   'city_id'=>$row['city_id'],
   'city_name'=>$row['city_name'],
   'state_id'=>$row['state_id']
    ));
}
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>
