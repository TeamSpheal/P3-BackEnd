package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {
    UserRepository userRepo;

    SearchServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * This method will query the User table and return all
     * Users that have the 'name' input inside their
     *
     * @param name (String)
     * @return
     */
    @Override
    public Optional<List<UserDTO>> queryUserTable(String name) {
        if (name.contains("_")) {
            String firstName = name.substring(0, name.indexOf("_"));
            String lastName = name.substring(name.indexOf("_") + 1, name.length() - 1);
            return userRepo.findAllByFirstNameStartingWithOrLastNameStartingWithOrderByFirstName(
                    firstName, lastName);
        } else {
            return userRepo.findAllByFirstNameStartingWithOrLastNameStartingWithOrderByFirstName(name, name);
        }
    }
}
