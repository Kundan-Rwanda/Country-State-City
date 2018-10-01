<?php
/*
 Created By Kundan on 28/10/2018
 Email: kundan.kumar011@gmail.com
*/
require_once('db_connection.php');

//Assign Varibale fetching from GET Method
$country_id=$_GET['country_id'];
$sql = "SELECT * FROM State WHERE country_id = $country_id";
$res = mysqli_query($con,$sql);
$result = array();
while($row = mysqli_fetch_array($res)){
 array_push($result,array(
   'state_id'=>$row['state_id'],
   'state_name'=>$row['state_name'],
   'country_id'=>$row['country_id']
    ));
}
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>
