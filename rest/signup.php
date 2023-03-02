<?php
require("rest_api_conn.php");
require("function.php");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST"); // here is define the request method



if($_SERVER["REQUEST_METHOD"] == "POST"){
    $response = array();

    $fname = $_POST["fname"];
    $lname = $_POST["lname"];
    $user = $_POST["user"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $pwd = $_POST["pass"];
    $pwdRepeat = $_POST["confirm_pass"];

    if(InvalidVid($user) !== false){
        $response['code']= 0;
        $response['message']='Only alphabet and number';
    }
    else if(InvalidEmail($email) !== false){
        $response['code']= 0;
        $response['message']='Only alphabet and number';
    }
    else if(uidExists($conn,$user,$email) !== false){
        $response['code']= 0;
        $response['message']="Username already taken";
    }
    else{
        createUser($conn,$fname,$lname,$user,$email,$phone,$pwd,$pwdRepeat);
        $response['code']= 1;
        $response['message']="Successfully Sign Up";
    }

    json_encode($response);    
    mysqli_close($conn);
}


