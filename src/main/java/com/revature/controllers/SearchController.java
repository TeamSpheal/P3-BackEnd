package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.SearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SearchController {
    SearchServiceImpl searchServ;

    SearchController(SearchServiceImpl searchServ) {
        this.searchServ = searchServ;
    }

    @GetMapping
    protected ResponseEntity<ArrayList<User>> getByName() {


        return null;
    }
}
