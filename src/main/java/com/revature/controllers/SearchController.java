package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.SearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchServiceImpl searchServ;

    SearchController(SearchServiceImpl searchServ) {
        this.searchServ = searchServ;
    }

    @GetMapping("/{name}")
    protected ResponseEntity<Optional<ArrayList<User>>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(searchServ.queryUserTable(name));
    }
}
