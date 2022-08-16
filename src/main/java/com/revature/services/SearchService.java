package com.revature.services;

import com.revature.models.Post;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SearchService {
    Optional<List<User>> queryUserTable(String name);

    
}
