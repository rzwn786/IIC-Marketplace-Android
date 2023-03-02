<?php
require("rest_api_conn.php");
header("Content-Type: application/json");
header("Acess-Control-Allow-Origin: *");
header("Acess-Control-Allow-Methods: POST"); // here is define the request method

$response = array();

if($_SERVER["REQUEST_METHOD"] == "POST"){

    $userid = $_POST['userid'];
    $title = $_POST['title'];
    $price = $_POST['price'];
    $categeries = $_POST['categories'];
    $condition = $_POST['condition'];
    $description = $_POST['description'];;
    $location = $_POST['location'];
    $cod = $_POST['cod'];
    $postage = $_POST['pos'];

     //image upload
     $fileName = $_FILES['upload_file']['name'];
     $fileTmpName = $_FILES['upload_file']['tmp_name'];
     $fileSize = $_FILES['upload_file']['size'];
     $fileError = $_FILES['upload_file']['error'];
 
     if($fileError === 0){
         if($fileSize < 20000000){
             $File_ex = pathinfo($fileName, PATHINFO_EXTENSION);
             $File_ex_lower = strtolower($File_ex);
             $allowed = array('jpg','jpeg','png');
 
             if(in_array($File_ex_lower, $allowed)){
                 $new_file_name = uniqid("IMG-",true).".".$File_ex_lower;
                 $img_path = "../uploads/".$new_file_name;
                 move_uploaded_file($fileTmpName,$img_path);

                 $sql ="INSERT INTO listing (user_id,item_title,item_price,item_categories,item_condition,item_description,item_location,item_cod,item_postage,created_date,item_image)
                 VALUES('$userid','$title','$price','$categeries','$condition','$description','$location','$cod','$postage',NOW(),'$new_file_name')";
                
                $execute = mysqli_query($conn,$sql);
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
                 $errorMessage = "Not supported this type of file";
                 echo $errorMessage;
                 //header("Location: sell.php?error=$errorMessage");
             }
         }
         else{
             $errorMessage = "Image is to big";
             echo $errorMessage;
             //header("Location: sell.php?error=$errorMessage");
         } 
     }
     else{
         $errorMessage = "unable to upload image";
         echo $errorMessage;
         //header("Location: sell.php?error=$errorMessage");
     }
}
else{
    $response["code"] = 0;
    $response["message"] = "No POST request was made";
}
echo json_encode($response);
mysqli_close($conn);