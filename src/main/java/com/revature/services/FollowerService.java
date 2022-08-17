package com.revature.services;

import com.revature.models.Follower;

public interface FollowerService {
  
     
    
        public boolean addFollower(Follower follower);
        public boolean removeFollower(Long follower_id, Long followed_id);

        
    
}
