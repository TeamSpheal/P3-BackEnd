package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.models.User;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    Optional<List<UserDTO>> queryUserTable(String name);
}
