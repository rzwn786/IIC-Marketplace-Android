<?php
require("rest_api_conn.php");
require_once 'function.php';
header("Content-Type: application/json");
header("Acess-Control-Allow-Origin: *");
header("Acess-Control-Allow-Methods: POST"); // here is define the request method

$response =array();

if($_SERVER["REQUEST_METHOD"] == "POST"){

    $fname = $_POST["fname"];
    $lname = $_POST["lname"];
    $user = $_POST["user"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $pwd = $_POST["pass"];
    $pwdRepeat = $_POST["confirm_pass"];

    if(emptyInputSignup ($fname,$lname, $user, $email,$phone,$pwd,$pwdRepeat) !== false){
        $response["code"] = 0;
        $response["message"] = "Empty Input User";
    }
    if(InvalidVid($user) !== false){
        $response["code"] = 0;
        $response["message"] = "Invalid User name";
    }
    if(InvalidEmail($email) !== false){
        $response["code"] = 0;
        $response["message"] = "Invalid User name";
    }
    if(pwdMatch($pwd,$pwdRepeat) !== false){
        $response["code"] = 0;
        $response["message"] = "Passwoed not match";
    }
    if(uidExists($conn,$user,$email) !== false){
        $response["code"] = 0;
        $response["message"] = "Username already taken";
    }
        createUser($conn,$fname,$lname,$user,$email,$phone,$pwd,$pwdRepeat);
        $check = mysqli_affected_rows($conn);
        
        if($check > 0){
            $response["code"] = 1;
            $response["message"] = "Data Saved";
        }
        else{
            $response["code"] = 0;
            $response["message"] = "Data not Saved";
        }
}
else{
    $response["code"] = 0;
    $response["message"] = "No POST request was made";
}
echo json_encode($response);
mysqli_close($conn);