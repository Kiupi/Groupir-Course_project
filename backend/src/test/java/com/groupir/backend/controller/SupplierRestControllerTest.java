package com.groupir.backend.controller;

import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.OrderItemRepository;
import com.groupir.backend.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@ComponentScan("com.groupir.backend.service")
@ComponentScan("com.groupir.backend.security")
@ActiveProfiles("test")
class SupplierRestControllerTest {

    Role admin;
    Role user;
    Role supplier;

    User theo;
    User cyril;
    User raphael;

    List<User> users;

    public SupplierRestControllerTest() {
        admin = new Role();
        user = new Role();
        supplier = new Role();

        admin.setRoleId(1);
        admin.setRoleName("ADMIN");
        user.setRoleId(2);
        user.setRoleName("USER");
        supplier.setRoleId(3);
        supplier.setRoleName("SUPPLIER");

        theo = new User();
        theo.setUserId(1);
        theo.setBirthDate(new Date());
        theo.setEmail("theo.basty@telecom-st-etienne.fr");
        theo.setFirstName("Théo");
        theo.setLastName("Basty");
        theo.setPassword("$2a$12$tSYPw1WzkuG/hmgMwtue/.duktRg.FQ9yVKGylUsup/AU8VHcnR3q");
        theo.setRole(admin);

        cyril = new User();
        cyril.setUserId(2);
        cyril.setBirthDate(new Date());
        cyril.setEmail("cyril.faisandier@telecom-st-etienne.fr");
        cyril.setFirstName("Cyril");
        cyril.setLastName("Faisandier");
        cyril.setPassword("none");
        cyril.setRole(user);

        raphael = new User();
        raphael.setUserId(3);
        raphael.setBirthDate(new Date());
        raphael.setEmail("raphael.chevasson@telecom-st-etienne.fr");
        raphael.setFirstName("Raphaël");
        raphael.setLastName("Chevasson");
        raphael.setPassword("none");
        raphael.setRole(supplier);

        users = Lists.list(theo, cyril, raphael);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void configureMocks() {
        given(userRepository.findByEmail(theo.getEmail())).willReturn(Optional.of(theo));
        given(userRepository.findByEmail(cyril.getEmail())).willReturn(Optional.of(cyril));
        given(userRepository.findByEmail(raphael.getEmail())).willReturn(Optional.of(raphael));
    }

    @Nested
    class AnonymousAccess {
        // /api/supplier/items
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToListItems() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/supplier/items")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/supplier/items/{optId}/{orderId}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{{\n" +
                    "  \"option\": {\n" +
                    "    \"optionId\": 1,\n" +
                    "  },\n" +
                    "  \"order\": {\n" +
                    "    \"orderId\": 1\n" +
                    "  },\n" +
                    "  \"dispatchmentDate\": \"2019-02-11T15:09:34.587Z\",\n" +
                    "  \"trackingNumber\": \"thisisatrackingnumber\"\n" +
                    "}}";

            MvcResult result = mockMvc.perform(post("/api/supplier/1/1")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }
    }

    @Nested
    class AdminAccess {
        // /api/supplier/items
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Anonymous_shouldNotBeAbleToListItems() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/supplier/items")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/supplier/items/{optId}/{orderId}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{{\n" +
                    "  \"option\": {\n" +
                    "    \"optionId\": 1,\n" +
                    "  },\n" +
                    "  \"order\": {\n" +
                    "    \"orderId\": 1\n" +
                    "  },\n" +
                    "  \"dispatchmentDate\": \"2019-02-11T15:09:34.587Z\",\n" +
                    "  \"trackingNumber\": \"thisisatrackingnumber\"\n" +
                    "}}";

            MvcResult result = mockMvc.perform(post("/api/supplier/1/1")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }
    }

    @Nested
    class UserAccess {
        // /api/supplier/items
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void Anonymous_shouldNotBeAbleToListItems() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/supplier/items")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/supplier/items/{optId}/{orderId}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{{\n" +
                    "  \"option\": {\n" +
                    "    \"optionId\": 1,\n" +
                    "  },\n" +
                    "  \"order\": {\n" +
                    "    \"orderId\": 1\n" +
                    "  },\n" +
                    "  \"dispatchmentDate\": \"2019-02-11T15:09:34.587Z\",\n" +
                    "  \"trackingNumber\": \"thisisatrackingnumber\"\n" +
                    "}}";

            MvcResult result = mockMvc.perform(post("/api/supplier/1/1")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }
    }

    @Nested
    class SupplierAccess {
        // /api/supplier/items
        @WithMockUser(username = "julien.subercaze@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToListItems() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/supplier/items")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/supplier/items/{optId}/{orderId}
        @WithMockUser(username = "julien.subercaze@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{{\n" +
                    "  \"option\": {\n" +
                    "    \"optionId\": 1,\n" +
                    "  },\n" +
                    "  \"order\": {\n" +
                    "    \"orderId\": 1\n" +
                    "  },\n" +
                    "  \"dispatchmentDate\": \"2019-02-11T15:09:34.587Z\",\n" +
                    "  \"trackingNumber\": \"thisisatrackingnumber\"\n" +
                    "}}";

            MvcResult result = mockMvc.perform(post("/api/supplier/1/1")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }
    }
}