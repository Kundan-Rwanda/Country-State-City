<?php
/*
 Created By Kundan on 28/10/2018
 Email: kundan.kumar011@gmail.com
*/
require_once('db_connection.php');
$sql = "SELECT * FROM Country";
$res = mysqli_query($con,$sql);
$result = array();
while($row = mysqli_fetch_array($res)){
 array_push($result,array(
   'country_id'=>$row['country_id'],
   'country_name'=>$row['country_name']
    ));
}
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>
