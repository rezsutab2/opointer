    
    
<?php
    require_once('config.php');
    
        $user_id=$_GET["user_id"];
    
        $stmt = mysqli_prepare($connect, "Select e.user_id,u.username,e.date,e.message,l.latitude,l.longitude from friendship f, user u, `event` e, location l where f.user1_id = ? and f.user2_id = u.user_id AND f.user2_id=e.user_id AND l.location_id=e.location_id OR f.user2_id = ? and f.user1_id=u.user_id AND f.user1_id=e.user_id AND f.isapproved=1 AND l.location_id=e.location_id");
        
        $stmt->bind_param('ii',$user_id,$user_id);
        $stmt->execute();
        $stmt->store_result();
        
        if ($stmt->num_rows === 0) exit;
        
        $response = array();
        
        for ($i = 0; $i < $stmt->num_rows; $i++)
        {
        $response[$i] = [];
    
        $stmt->bind_result($response[$i]["user_id"],$response[$i]['username'],$response[$i]['date'],$response[$i]['message'],$response[$i]['latitude'],$response[$i]['longitude']);
    
        $stmt->fetch();
        }
        echo json_encode($response);