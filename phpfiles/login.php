    
<?php
    require_once('config.php');
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $stmt = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
    
    $stmt->bind_param('s',$username);
    $stmt->execute();
    $stmt->store_result();
    
    if ($stmt->num_rows === 0) exit;
    
    $response = array();
    
    
    $stmt->bind_result($response['user_id'],$response['username'],$response['password'],$response['name']);
            
    while($stmt->fetch()){
        if(password_verify($password,$response['password'])){
            $response["verified"]=true;
        }
        else{
            $response["verified"] = false;
        }
    }
    
    

    echo json_encode($response);