<?php 
	include_once 'config.php';
	$json = file_get_contents('php://input');
	$obj = json_decode($json);
	if(empty($obj))
	{
		echo json_encode(array('error'=>"Invalid Access...!"));
		exit();
	}
	
	$RESPONSE_TYPE="-1";
	$user=array();
	// getting values from database
	$result=mysqli_query($link,"SELECT * FROM users WHERE cnic='".$obj->{'cnic'}."' AND password='".$obj->{'pw'}."'");
	if(!$result){echo "MYSQL_ERROR:".mysqli_error($link);exit();}
	if(mysqli_num_rows($result)==0)
	{
		$RESPONSE_TYPE="0";
	}
	else
	{
		$RESPONSE_TYPE="1";
		$row=mysqli_fetch_array($result);
		$row['department']="";
		$user[]=$row;
	}

	$arr=array('type'=>$RESPONSE_TYPE,'user'=>$user);
	echo json_encode($arr);
?>