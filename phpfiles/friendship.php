    
    
<?php
    require_once('config.php');
    
        $user_id=$_GET["user_id"];
    
        $stmt = mysqli_prepare($connect, "Select u.*,f.isapproved from friendship f, user u where f.user1_id = ? and f.user2_id = u.user_id OR f.user2_id = ? and f.user1_id=user_id AND f.isapproved=1 ORDER BY f.isapproved");
        
        $stmt->bind_param('ii',$user_id,$user_id);
        $stmt->execute();
        $stmt->store_result();
        
        if ($stmt->num_rows === 0) exit;
        
        $response = array();
        
        for ($i = 0; $i < $stmt->num_rows; $i++)
        {
        $response[$i] = [];
    
        $stmt->bind_result($response[$i]['user_id'],$response[$i]['username'],$response[$i]['password'],$response[$i]['name'],$response[$i]['birthdate'],$response[$i]['isapproved']);
    
        $stmt->fetch();
        }
        echo json_encode($response);