<?php 

include_once 'config.php';
	$json = file_get_contents('php://input');
	$obj = json_decode($json);
	if(empty($obj))
	{
		echo "Invalid Access...!";
		exit();
	}

// $user_id=$obj->{'user_id'};

$RESPONSE_TYPE="-1";
$ERROR="Not implemented yet";


	$arr=array('type'=>$RESPONSE_TYPE,'error'=>$ERROR);
	echo json_encode($arr);

?>