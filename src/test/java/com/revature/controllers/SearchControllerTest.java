package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.UserDTO;
import com.revature.repositories.UserRepository;
import com.revature.services.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SearchController.class)
public class SearchControllerTest {

    @MockBean
    private SearchServiceImpl searchService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSearchForUser() throws JsonProcessingException, Exception {
        List<UserDTO> mockUsers = new ArrayList<>();

        Mockito.when(searchService.queryUserTable("test")).thenReturn(Optional.of(mockUsers));

        mockMvc.perform(get("/search/test"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockUsers)));
    }
    
    @Test
    void cannotSearchForUser() throws JsonProcessingException, Exception {
        List<UserDTO> mockUsers = new ArrayList<>();

        Mockito.when(searchService.queryUserTable("test")).thenReturn(Optional.empty());

        mockMvc.perform(get("/search/test"))
                .andExpect(status().isBadRequest());
                
    }
    
    
}
