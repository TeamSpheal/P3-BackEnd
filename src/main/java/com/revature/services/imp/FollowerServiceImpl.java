package com.revature.services.imp;

import org.springframework.stereotype.Service;

import com.revature.models.Follower;
import com.revature.repositories.FollowerRepo.FollowerRepository;
import com.revature.services.FollowerService;
@Service
public class FollowerServiceImpl implements FollowerService {

    public FollowerServiceImpl(FollowerRepository followerRepository){

        this.followerRepository=followerRepository;
    }

    private final FollowerRepository followerRepository;

    public boolean addFollower(Follower followerId) {
    	try {
        	followerRepository.save( followerId);
            return true;
        	 
    	}catch (Exception e) {
    		e.getStackTrace(); 
            return false;
        }	 
    }

	public boolean removeFollower(Long followedId, Long followerId) {
		 
		try {
			followerRepository.removeFollower(followedId, followerId);
            return true;
			 
		}catch(Exception e) {
			e.printStackTrace();
            return false;
		 
		}
	}
    
}
