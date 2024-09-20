<?php
//if(!isset($_SESSION)) {  session_start(); }
	define('DB_HOST', 'localhost');
    define('DB_USR', 'root');
    define('DB_PW', '');
    define('DB', 'meeting');
	
 //Connect to mysql server
  $link = mysqli_connect(DB_HOST, DB_USR, DB_PW);
  if(!$link) {
    die('Failed to connect to server: ' . mysqli_error($link));
  }
        //Select database
  $db = mysqli_select_db($link,DB);
  if(!$db) {
    die("Unable to select database");
  }

?>