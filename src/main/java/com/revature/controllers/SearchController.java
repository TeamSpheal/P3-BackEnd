package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.SearchServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchServiceImpl searchServ;

    public SearchController(SearchServiceImpl searchServ) {
        this.searchServ = searchServ;
    }

    
    /** 
     * @param name
     * @return ResponseEntity<ArrayList<User>>
     */
    @GetMapping("/{name}")
    protected ResponseEntity<ArrayList<User>> getByName(@PathVariable String name) {
        Optional<ArrayList<User>> oUserList = searchServ.queryUserTable(name);
        if (oUserList.isPresent()) {
            return ResponseEntity.ok(oUserList.get());
        }
        // If list is not present then return 400.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
