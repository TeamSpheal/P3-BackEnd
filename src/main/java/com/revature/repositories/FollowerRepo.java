package com.revature.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.models.Follower;

@Repository
public class FollowerRepo {
    
 public interface FollowerRepository extends JpaRepository<Follower, Long>{
         
        public Follower findByFollowerId(long followerId);

        public void removeFollower(Long followedId, Long followerId);

         
        
    
    }
}
    