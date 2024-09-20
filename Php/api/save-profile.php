<?php 

include_once 'config.php';
	$json = file_get_contents('php://input');
	$obj = json_decode($json);
	if(empty($obj))
	{
		echo "Invalid Access...!";
		exit();
	}

$user_id=$obj->{'user_id'};
$user_name=$obj->{'user_name'};
$gender=$obj->{'gender'};
$age=$obj->{'age'};
$contact=$obj->{'contact'};
$designation=$obj->{'designation'};
$address=$obj->{'address'};

$RESPONSE_TYPE="-1";
$ERROR="Some unknown Error.";
	$q="UPDATE users SET user_name='$user_name',gender='$gender',address='$address',age='$age',contact='$contact',designation='$designation' WHERE user_id='$user_id')";
	$result=mysqli_query($link,$q);
	if(!$result){$RESPONSE_TYPE=-1;$ERROR="MYSQL_ERROR:".mysqli_error($link);}
	$RESPONSE_TYPE="1";
	$ERROR="Done";

	$arr=array('type'=>$RESPONSE_TYPE,'error'=>$ERROR);
	echo json_encode($arr);

?>