package com.revature.controllers;

import com.revature.dtos.UserDTO;
import com.revature.services.SearchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchServiceImpl searchServ;

    SearchController(SearchServiceImpl searchServ) { this.searchServ = searchServ;}

    @GetMapping("/{name}")
    protected ResponseEntity<Optional<List<UserDTO>>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(searchServ.queryUserTable(name));
    }
}
