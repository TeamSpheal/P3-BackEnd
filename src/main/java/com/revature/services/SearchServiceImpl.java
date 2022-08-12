package com.revature.services;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {
    UserRepository userRepo;
    PostRepository postRepo;

    SearchServiceImpl(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    public Optional<ArrayList<User>> queryUserTable(String name) {
        Optional<ArrayList<User>> users = userRepo.findAllByFirstNameContaining(name);


        return users;
    }

    @Override
    public List<Post> queryPostTable(String post) {
        return null;
    }
}
