    
<?php
    require_once('config.php');
    
    $username = $_GET["username"];
    $password = $_GET["password"];
    
    $stmt = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
    
    $stmt->bind_param('s',$username);
    $stmt->execute();
    $stmt->store_result();
    
    if ($stmt->num_rows === 0) exit;
    
    $response = array();
    
    for ($i = 0; $i < $stmt->num_rows; $i++)
        {
        $response[$i] = [];
    
        $stmt->bind_result($response[$i]['user_id'],$response[$i]['username'],$response[$i]['password'],$response[$i]['name'],$response[$i]['birthdate']);
            
            while($stmt->fetch()){
                if(password_verify($password,$response[$i]['password'])){
                $response[$i]["verified"]=true;
                }
                else{
                    $response[$i]["verified"] = false;
                }
            }
        }
    

    echo json_encode($response);