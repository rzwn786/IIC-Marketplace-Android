<?php

function emptyInputSignup ($fname,$lname, $user, $email,$phone,$pwd,$pwdRepeat){
 $result;
 if (empty($fname)||empty($lname)||empty($user)||empty($email)||empty($phone)||empty($pwd)||empty($pwdRepeat)){

    $result=true;
 }
 else{
    $result=false;
 }
 return $result;
}

function InvalidVid ($user){
    $result;
    if(!preg_match("/^[a-zA-Z0-9]*$/",$user)){
       $result=true;
    }
    else{
       $result=false;
    }
    return $result;
}

function InvalidEmail ($email){
    $result;
    if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
       $result=true;
    }
    else{
       $result=false;
    }
    return $result;
}

function pwdMatch ($pwd,$pwdRepeat){
    $result;
    if($pwd !== $pwdRepeat){
       $result=true;
    }
    else{
       $result=false;
    }
    return $result;
}

function uidExists ($conn,$user,$email){
 $sql = "SELECT * FROM users WHERE userName = ? OR useremail = ?;";
 $stmt = mysqli_stmt_init($conn);

 if(!mysqli_stmt_prepare($stmt,$sql)){
    //header("location: ../signup.php?error=stmtfailed");

    exit();
 }

mysqli_stmt_bind_param($stmt,"ss",$user,$email);
mysqli_stmt_execute($stmt);

$resultData= mysqli_stmt_get_result($stmt);

if($row = mysqli_fetch_assoc($resultData)){
    return $row;
}
else{
    return false;
    return $return;
    
}
return $return;
mysqli_stmt_close($stmt);

}

function createUser($conn,$fname,$lname,$user,$email,$phone,$pwd){
    $sql = "INSERT INTO users(firstName,lastName,userName,useremail,userPhone,userPwd) VALUE (?,?,?,?,?,?);";
    $stmt = mysqli_stmt_init($conn);
   
    if(!mysqli_stmt_prepare($stmt,$sql)){
       //header("location: ../signup.php?error=STNTFAILED");
       exit();
    }
   
   $hashedPwd =password_hash($pwd,PASSWORD_DEFAULT);


   mysqli_stmt_bind_param($stmt,"ssssss",$fname,$lname,$user,$email,$phone,$hashedPwd);
   mysqli_stmt_execute($stmt);
   mysqli_stmt_close($stmt);
   //header("location: ../signup.php?error=none");

   }

   function emptyInputLogin ($user,$pwd){
      $result;
      if (empty($user)||empty($pwd)){
     
         $result=true;
      }
      else{
         $result=false;
      }
      return $result;
     }

     function loginUser($conn,$user,$pwd){
      $uidExists = uidExists ($conn,$user,$user);

      if($uidExists === false){
         header("location: ../login.php?error=noDataFound");
         exit();
      }

      $pwdHashed = $uidExists["userPwd"];
      $checkPwd = password_verify($pwd,$pwdHashed);

      if($checkPwd === false){
         header("location: ../login.php?error=wrongpassword");
         exit();
      }
      else if($checkPwd === true){
         session_start();
         $_SESSION["userid"]=$uidExists["usersId"];
         $_SESSION["useruid"]=$uidExists["userName"];
         header("location: ../home.php");
         exit();
   
      }
     }
