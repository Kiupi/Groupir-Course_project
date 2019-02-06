package com.groupir.backend.controller;

import com.groupir.backend.model.User;
import com.groupir.backend.repository.RoleRepository;
import com.groupir.backend.repository.UserRepository;
import com.groupir.backend.service.ServiceUser;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@AutoConfigureTestDatabase
@ComponentScan("com.groupir.backend.security")
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUser serviceUser;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;


    @Test
    void shouldCode200ForGetRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/list");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void shouldCode204ForGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/user/list");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(204, result.getResponse().getStatus());
    }


    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}