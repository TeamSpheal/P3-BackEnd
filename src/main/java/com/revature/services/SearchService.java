package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.Optional;

public interface SearchService {

    Optional<ArrayList<User>> queryUserTable(String name);

}
