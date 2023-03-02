<?php

require("rest_api_conn.php");

if($_POST){

    //POST DATA
    $fname = $_POST["fname"];
    $lname = $_POST["lname"];
    $user = $_POST["user"];
    $email = $_POST["email"];
    $phone = $_POST["phone"];
    $pwd = $_POST["pass"];
    $pwdRepeat = $_POST["confirm_pass"];

    $response = [];

    //Check redundant username
    $userQuery = $conn->prepare("SELECT * FROM users where userName = ?");
    $userQuery->execute(array($user));


    if($userQuery->num_rows() != 0){
        
        $response['status']= false;
        $response['message']='Akun sudah digunakan';
    }
    else {
        $insertAccount = 'INSERT INTO users(firstName,lastName,userName,useremail,userPhone,userPwd) values (:fname, :lname, :user, :email, :phone, :pwd)';
        $statement = $conn->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
               

                ':fname' => $fname,
                ':lname' => $lname,
                ':user' => $user,
                ':email' => $email,
                ':phone' => $phone,
                ':pass' => md5($pwd)


            ]);

            //Beri response
            $response['code']= 1;
            $response['message']='Sucessfully Registered';
            $response['data'] = [

                ':fname' => $fname,
                ':lname' => $lname,
                ':user' => $user,
                ':email' => $email,
                ':phone' => $phone

            ];
        } catch (Exception $e){
            die($e->getMessage());
        }

    }
    
    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print JSON
    echo $json;
}