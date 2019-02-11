package com.groupir.backend.controller;

import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.RoleRepository;
import com.groupir.backend.repository.UserRepository;
import com.groupir.backend.service.ServiceOrderItem;
import com.groupir.backend.service.ServiceUser;
import com.groupir.backend.service.ServiceUserOrders;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@AutoConfigureTestDatabase
@ComponentScan("com.groupir.backend.security")
class UserRestControllerTest {
    Role admin;
    Role user;
    Role supplier;

    User theo;
    User cyril;
    User raphael;

    List<User> users;


    public UserRestControllerTest() {
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

    @MockBean
    private ServiceUser serviceUser;
    @MockBean
    private ServiceOrderItem serviceOrderItem;
    @MockBean
    private ServiceUserOrders serviceUserOrders;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void configureMocks() {
        given(serviceUser.add(any(User.class))).willAnswer(invocation -> {
            User newUser = invocation.getArgument(0);
            newUser.setUserId(7);
            return newUser;
        });
        given(serviceUser.findAllUser()).willReturn(users);
        given(serviceUser.update(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        given(serviceUser.findIfExist(1)).willReturn(true);
        given(serviceUser.findIfExist(2)).willReturn(true);
        given(serviceUser.findIfExist(3)).willReturn(true);

        given(userRepository.findByEmail(theo.getEmail())).willReturn(Optional.of(theo));
        given(serviceUser.findByEmail(theo.getEmail())).willReturn(Optional.of(theo));
        given(userRepository.findByEmail(cyril.getEmail())).willReturn(Optional.of(cyril));
        given(serviceUser.findByEmail(cyril.getEmail())).willReturn(Optional.of(cyril));
        given(userRepository.findByEmail(raphael.getEmail())).willReturn(Optional.of(raphael));
        given(serviceUser.findByEmail(raphael.getEmail())).willReturn(Optional.of(raphael));
    }

    @Nested
    class AnonymousAccess {
        // /api/user/add
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToRegisterUser() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToRegisterAdmin() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToRegisterSupplier() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 3\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/list
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToListUser() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/user/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/login
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToLogin() throws Exception {
            String authenticationRequestJson = "{\n" +
                    "\"email\":\"theo.basty@telecom-st-etienne.fr\",\n" +
                    "\"password\":\"0000\"\n" +
                    "}";
            MvcResult result = mockMvc.perform(post("/api/user/login")
                    .content(authenticationRequestJson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/update/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToUpdateAnUser() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 1," +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"Updated\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"0000\"\n" +
                    "  }";
            MvcResult result = mockMvc.perform(put("/api/user/update/1")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/delete/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotAbleToDeleteAnUser() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

    }

    @Nested
    class AdminAccess {
        // /api/user/add
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToRegisterUser() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToRegisterAdmin() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"Admin\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToRegisterSupplier() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"Supplier\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 3\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/list
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToListUser() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/user/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/login
        // not relevant, an user can just login as an other user by not sending it's authorization token

        // /api/user/update/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToUpdateHimself() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 1,\n" +
                    "    \"lastName\": \"Théo\",\n" +
                    "    \"firstName\": \"Basty\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"theo.basty@gmail.com\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"1234\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(put("/api/user/update/1")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToUpdateAnOtherUser() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 2,\n" +
                    "    \"lastName\": \"Cyril\",\n" +
                    "    \"firstName\": \"Faisandier\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"cyril.faisandier@gmail.com\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"1234\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(put("/api/user/update/2")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/delete/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldBeAbleToDeleteAnOtherUser() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/2")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void Admin_shouldNotBeAbleToDeleteHimself() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

    }

    @Nested
    class UserAccess {
        // /api/user/add
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldBeAbleToRegisterUser() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldNotBeAbleToRegisterAdmin() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldNotBeAbleToRegisterSupplier() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 3\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/list
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldNotBeAbleToListUser() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/user/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/login
        // not relevant, an user can just login as an other user by not sending it's authorization token

        // /api/user/update/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldNotBeAbleToUpdateAnOtherUser() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 1," +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"Updated\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"0000\"\n" +
                    "  }";
            MvcResult result = mockMvc.perform(put("/api/user/update/1")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldBeAbleToUpdateHimself() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 2," +
                    "    \"lastName\": \"Faisandier\",\n" +
                    "    \"firstName\": \"Cyril\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"updated.email@telecom-st-etienne.fr\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"0000\"\n" +
                    "  }";
            MvcResult result = mockMvc.perform(put("/api/user/update/2")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/delete/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void User_shouldNotAbleToDeleteAnOtherUser() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void Anonymous_shouldBeAbleToDeleteHimself() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/2")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }
    }

    @Nested
    class SupplierAccess {
        // /api/user/add
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldBeAbleToRegisterUser() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 2\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToRegisterAdmin() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToRegisterSupplier() throws Exception {
            String newUserJSon = "  {\n" +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"New\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 3\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"test\"\n" +
                    "  }";

            MvcResult result = mockMvc.perform(post("/api/user/add")
                    .content(newUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/list
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToListUser() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/user/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/user/login
        // not relevant, an user can just login as an other user by not sending it's authorization token

        // /api/user/update/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldNotBeAbleToUpdateAnOtherUser() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 1," +
                    "    \"lastName\": \"User\",\n" +
                    "    \"firstName\": \"Updated\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 1\n" +
                    "    },\n" +
                    "    \"email\": \"new.user@test.test\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"0000\"\n" +
                    "  }";
            MvcResult result = mockMvc.perform(put("/api/user/update/1")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Supplier_shouldBeAbleToUpdateHimself() throws Exception {
            String updatedUserJSon = "  {\n" +
                    "    \"userId\": 3," +
                    "    \"lastName\": \"Chevasson\",\n" +
                    "    \"firstName\": \"Raphaël\",\n" +
                    "    \"birthDate\": null,\n" +
                    "    \"role\": {\n" +
                    "      \"roleId\": 3\n" +
                    "    },\n" +
                    "    \"email\": \"updated.email@telecom-st-etienne.fr\",\n" +
                    "    \"defaultAddress\": null,\n" +
                    "    \"password\": \"0000\"\n" +
                    "  }";
            MvcResult result = mockMvc.perform(put("/api/user/update/3")
                    .content(updatedUserJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/user/delete/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void User_shouldNotAbleToDeleteAnOtherUser() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void Anonymous_shouldNotBeAbleToDeleteHimself() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/user/delete/3")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }
    }

}