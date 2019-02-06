package com.groupir.backend.controller;

import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.RoleRepository;
import com.groupir.backend.repository.UserRepository;
import com.groupir.backend.service.ServiceUser;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@Sql(scripts = {"/sql/groupir_address.sql"})
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUser serviceUser;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;


    @AfterEach
    @Sql({"/sql/drop.sql"})
    void initDelete(){}

private String exampleUserAddJson = "{\"birthDate\": \"1996-01-19\",\n" +
                                        "  \"defaultAddress\": null \n"+
                                        "  \"email\": \"paul15@gmail.com\",\n" +
                                        "  \"firstName\": \"Paul\",\n" +
                                        "  \"lastName\": \"Dujardin\",\n" +
                                        "  \"password\": \"toto\",\n" +
                                        "  \"role\": {\n" +
                                        "    \"roleId\": 1,\n" +
                                        "    \"roleName\": \"ADMIN\"\n" +
                                        "  },\n" +
                                        "}";



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