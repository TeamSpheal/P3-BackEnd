package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {
    UserRepository userRepo;
    PostRepository postRepo;

    SearchServiceImpl(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        
    }

    /**
     * This method will query the User table and return all
     * Users that have the 'name' input inside their
     *
     * @param name (String)
     * @return
     */
    @Override
    public Optional<ArrayList<User>> queryUserTable(String name) {
        return userRepo.findAllByFirstNameContaining(name);
    }

}
