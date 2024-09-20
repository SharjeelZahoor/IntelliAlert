<?php 

include_once 'config.php';
	$json = file_get_contents('php://input');
	$obj = json_decode($json);
	if(empty($obj))
	{
		echo "Invalid Access...!";
		exit();
	}
	// $arr=array('type'=>0,'error'=>"hello");
	// echo json_encode($arr);
	// exit();
$cnic=$obj->{'cnic'};
$password=$obj->{'password'};
$user_name=$obj->{'user_name'};
$gender=$obj->{'gender'};
$user_type=$obj->{'user_type'};
$age=$obj->{'age'};
$contact=$obj->{'contact'};
$designation=$obj->{'designation'};
$address=$obj->{'address'};

$RESPONSE_TYPE="-1";
$ERROR="Some unknown Error.";
	$result=mysqli_query($link,"SELECT * FROM users WHERE cnic='".$obj->{'cnic'}."'");
	if(!$result){$RESPONSE_TYPE=-1;$ERROR="MYSQL_ERROR:".mysqli_error($link);}
	if(mysqli_num_rows($result)>0)
	{
		$RESPONSE_TYPE="1";
		$ERROR="User exists.";
	}
	else
	{
		$q="INSERT INTO users (user_name,cnic,password,user_type,gender,address,age,contact,designation) VALUES('$user_name','$cnic','$password','$user_type','$gender','$address','$age','$contact','$designation')";
		$result=mysqli_query($link,$q);
		if(!$result){$RESPONSE_TYPE=-1;$ERROR="MYSQL_ERROR:".mysqli_error($link);}
		$RESPONSE_TYPE="2";
		$ERROR="Done";
	}

	$arr=array('type'=>$RESPONSE_TYPE,'error'=>$ERROR);
	echo json_encode($arr);

?>