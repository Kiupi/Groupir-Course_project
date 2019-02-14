package com.groupir.backend.controller;

import com.groupir.backend.model.*;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    User julien;
    User raphael;
    User jacques;

    OrderItemKey jsOIKey;
    OrderItemKey jfOIKey;
    OrderItem jsOI;
    OrderItem jfOI;

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

        raphael = new User();
        raphael.setUserId(4);
        raphael.setBirthDate(new Date());
        raphael.setEmail("raphael.chevasson@telecom-st-etienne.fr");
        raphael.setFirstName("Raphaël");
        raphael.setLastName("Chevasson");
        raphael.setPassword("$2a$12$c5IQwOw6ozbJbW5CdpVOjOKxd/ApEPNzGW425NFfAw35h2sLJ3kcG");
        raphael.setRole(user);

        julien = new User();
        julien.setUserId(5);
        julien.setBirthDate(new Date());
        julien.setEmail("julien.subercaze@telecom-st-etienne.fr");
        julien.setFirstName("Julien");
        julien.setLastName("Subercaze");
        julien.setPassword("$2a$12$myAby57ot5nLdV6CFyjXhe1N8WSDWdScQ69o2Q4TAEv8JWeXjclvO");
        julien.setRole(supplier);

        jacques = new User();
        jacques.setUserId(6);
        jacques.setBirthDate(new Date());
        jacques.setEmail("jacques.fayolle@telecom-st-etienne.fr");
        jacques.setFirstName("Jacques");
        jacques.setLastName("Fayolle");
        jacques.setPassword("$2a$12$TGo1tT47VfjrvTzWELuI7.NzLn7f5CtYco5VUrlirqCTnvE4CQwCO");
        jacques.setRole(supplier);

        users = Lists.list(theo, julien, raphael, jacques);

        //OrderItems

        jsOIKey = new OrderItemKey(
                new ProductOption(
                        11L,
                        new Product(6L, julien, null, null, null, null, null, null, null
                        ), null, null, null),
                new Order(1L, null, null, null, null)
        );
        jsOI = new OrderItem(
                jsOIKey,
                null,
                null,
                4
        );

        jfOIKey = new OrderItemKey(
                new ProductOption(
                        3L,
                        new Product(null, jacques, null, null, null, null, null, null, null
                        ), null, null, null),
                new Order(2L, null, null, null, null)
        );
        jfOI = new OrderItem(
                jfOIKey,
                null,
                null,
                10
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void configureMocks() {
        given(userRepository.findByEmail(theo.getEmail())).willReturn(Optional.of(theo));
        given(userRepository.findByEmail(julien.getEmail())).willReturn(Optional.of(julien));
        given(userRepository.findByEmail(raphael.getEmail())).willReturn(Optional.of(raphael));

        given(orderItemRepository.findById(new OrderItemKey(1L, 11L))).willReturn(Optional.of(jsOI));
        given(orderItemRepository.findById(new OrderItemKey(2L, 3L))).willReturn(Optional.of(jfOI));
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

        // /api/supplier/items/{orderId}/{optId}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{\n" +
                    "  \"dispatchmentDate\": \"2019-02-13T12:47:41.755Z\",\n" +
                    "  \"optionId\": 11,\n" +
                    "  \"orderId\": 1,\n" +
                    "  \"trackingNumber\": \"thisIsMyItem\"\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/supplier/items/1/1")
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

        // /api/supplier/items/{orderId}/{optId}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{\n" +
                    "  \"dispatchmentDate\": \"2019-02-13T12:47:41.755Z\",\n" +
                    "  \"optionId\": 11,\n" +
                    "  \"orderId\": 1,\n" +
                    "  \"trackingNumber\": \"thisIsMyItem\"\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/supplier/items/1/1")
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

        // /api/supplier/items/{orderId}/{optId}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void Anonymous_shouldNotBeAbleToChangeItemStatus() throws Exception {
            String newUserJSon = "{\n" +
                    "  \"dispatchmentDate\": \"2019-02-13T12:47:41.755Z\",\n" +
                    "  \"optionId\": 11,\n" +
                    "  \"orderId\": 1,\n" +
                    "  \"trackingNumber\": \"thisIsMyItem\"\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/supplier/items/1/1")
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
        void Supplier_shouldBeAbleToListItems() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/supplier/items")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/supplier/items/{orderId}/{optId}
        @WithMockUser(username = "julien.subercaze@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldBeAbleToChangeOwnItemStatus() throws Exception {
            String newUserJSon = "{\n" +
                    "  \"dispatchmentDate\": \"2019-02-13T12:47:41.755Z\",\n" +
                    "  \"optionId\": 11,\n" +
                    "  \"orderId\": 1,\n" +
                    "  \"trackingNumber\": \"thisIsMyItem\"\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/supplier/items/1/11")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "julien.subercaze@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToChangeOtherItemStatus() throws Exception {
            String newUserJSon = "{\n" +
                    "  \"dispatchmentDate\": \"2019-02-13T12:47:41.755Z\",\n" +
                    "  \"optionId\": 3,\n" +
                    "  \"orderId\": 2,\n" +
                    "  \"trackingNumber\": \"thisIsNotMyItem\"\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/supplier/items/2/3")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }
    }
}