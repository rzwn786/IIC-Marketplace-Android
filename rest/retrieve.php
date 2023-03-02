<?php
require("rest_api_conn.php");

$sql = "SELECT * FROM listing ORDER BY listingid DESC";
$result = mysqli_query($conn,$sql);
$row = mysqli_affected_rows($conn);

if($row > 0) {
    $response["code"] = 1;
    $response["message"] = "Data available";
    $response["data"] = array();

    while($take = mysqli_fetch_object($result)){
        $db["listingid"] = $take->listingid;
        $db["user_Id"] = $take->user_Id;
        $db["item_title"] = $take->item_title;
        $db["item_price"] = $take->item_price;
        $db["item_categories"] = $take->item_categories;
        $db["item_condition"] = $take->item_condition;
        $db["item_description"] = $take->item_description;
        $db["item_location"] = $take->item_location;
        $db["item_cod"] = $take->item_cod;
        $db["item_postage"] = $take->item_postage;
        $db["created_date"] = $take->created_date;
        $db["item_image"] = $take->item_image;

        array_push($response["data"],$db);


    }
}
else {
    $response["code"] = 0;
    $response["message"] = "Data not available";
}

echo json_encode($response);
mysqli_close($conn);