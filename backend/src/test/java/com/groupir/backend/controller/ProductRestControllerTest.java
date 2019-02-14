package com.groupir.backend.controller;

import com.groupir.backend.model.*;
import com.groupir.backend.repository.CategoryRepository;
import com.groupir.backend.repository.ProductRepository;
import com.groupir.backend.repository.RoleRepository;
import com.groupir.backend.repository.UserRepository;
import com.groupir.backend.service.ServiceOrderItem;
import com.groupir.backend.service.ServiceProduct;
import com.groupir.backend.service.ServiceUser;
import com.groupir.backend.service.ServiceUserOrders;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableWebMvc
@WebMvcTest(ProductRestController.class)
@AutoConfigureTestDatabase
@ComponentScan("com.groupir.backend.security")
class ProductRestControllerTest {

    private Role admin;
    private Role user;
    private Role supplier;

    private User theo;
    private User cyril;
    private User raphael;
    private Product object;
    private Category category;
    private List<User> users;


    public ProductRestControllerTest() {
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
        theo.setEnabled(true);

        cyril = new User();
        cyril.setUserId(2);
        cyril.setBirthDate(new Date());
        cyril.setEmail("cyril.faisandier@telecom-st-etienne.fr");
        cyril.setFirstName("Cyril");
        cyril.setLastName("Faisandier");
        cyril.setPassword("none");
        cyril.setRole(user);
        cyril.setEnabled(true);

        raphael = new User();
        raphael.setUserId(3);
        raphael.setBirthDate(new Date());
        raphael.setEmail("raphael.chevasson@telecom-st-etienne.fr");
        raphael.setFirstName("Raphaël");
        raphael.setLastName("Chevasson");
        raphael.setPassword("none");
        raphael.setRole(supplier);
        raphael.setEnabled(true);

        users = Lists.list(theo, cyril, raphael);
        object = new Product();
        category = new Category();
        ProductOption productOption = new ProductOption();
        Step step = new Step();

        step.setStepId(1L);
        step.setProduct(object);
        step.setThreshold(10);

        category.setCategoryId(1);
        category.setName("cat");

        productOption.setOptionId(1L);
        productOption.setProduct(object);
        productOption.setOptionName("blue");


        object.setProductId(1L);
        object.setName("pen");
        object.setManufacturer(raphael);
        object.setMaxSales(1000L);
        object.setEndDate(new Date());
        object.setDescription("bla bla");
        object.setCategory(category);
        object.setProductOptions(Arrays.asList(productOption));
        object.setSteps(Arrays.asList(step));
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceProduct serviceProduct;

    @MockBean
    private ServiceOrderItem serviceOrderItem;
    @MockBean
    private ServiceUserOrders serviceUserOrders;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private ServiceUser serviceUser;

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

        given(productRepository.findById(1L)).willReturn(Optional.of(object));
        given(serviceProduct.findById(1L)).willReturn(true);
        given(categoryRepository.findById(1)).willReturn(Optional.of(category));
    }

    @Nested
    class AnonymousAccess {
        // /api/product/list
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToListProduct() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/product/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/add
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToAddProduct() throws Exception {
            String newProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/product/add")
                    .content(newProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/update/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToUpdateProduct() throws Exception {
            String updateProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";
            MvcResult result = mockMvc.perform(put("/api/product/update/1")
                    .content(updateProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/delete/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldNotBeAbleToDeleteProduct() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/product/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/find/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToFindProduct() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/find/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findByCategory/{id}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToFindProductsByCategory() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findByCategory/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findProductsByName/{name}
        @WithAnonymousUser
        @Test
        void Anonymous_shouldBeAbleToFindProductsByName() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findProductsByName/pe")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }
    }

    @Nested
    class AdminAccess {

        // /api/product/list
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToListProduct() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/product/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/add
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToAddProduct() throws Exception {
            String newProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/product/add")
                    .content(newProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/update/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToUpdateProduct() throws Exception {
            String updateProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";
            MvcResult result = mockMvc.perform(put("/api/product/update/1")
                    .content(updateProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/delete/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToDeleteProduct() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/product/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/find/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToFindProduct() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/find/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findByCategory/{id}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToFindProductsByCategory() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findByCategory/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findProductsByName/{name}
        @WithMockUser(username = "theo.basty@telecom-st-etienne.fr", authorities = {"ADMIN"})
        @Test
        void admin_shouldBeAbleToFindProductsByName() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findProductsByName/pe")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

    }

    @Nested
    class UserAccess {
        // /api/product/list
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldBeAbleToListProduct() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/product/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/add
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldNotBeAbleToAddProduct() throws Exception {
            String newProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/product/add")
                    .content(newProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/update/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldNotBeAbleToUpdateProduct() throws Exception {
            String updateProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";
            MvcResult result = mockMvc.perform(put("/api/product/update/1")
                    .content(updateProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/delete/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldNotBeAbleToDeleteProduct() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/product/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/find/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldBeAbleToFindProduct() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/find/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findByCategory/{id}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldBeAbleToFindProductsByCategory() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findByCategory/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findProductsByName/{name}
        @WithMockUser(username = "cyril.faisandier@telecom-st-etienne.fr", authorities = {"USER"})
        @Test
        void user_shouldBeAbleToFindProductsByName() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findProductsByName/pe")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

    }

    @Nested
    class SupplierAccess {
        // /api/product/list
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldBeAbleToListProduct() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/product/list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/add
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldNotBeAbleToAddProduct() throws Exception {
            String newProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";

            MvcResult result = mockMvc.perform(post("/api/product/add")
                    .content(newProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/update/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldNotBeAbleToUpdateProduct() throws Exception {
            String updateProductJSon = "  {\n" +
                    "  \"category\": {\n" +
                    "    \"categoryId\": 1\n" +
                    "  },\n" +
                    "  \"description\": \"new product\",\n" +
                    "  \"endDate\": \"2019-02-13T15:07:07.479Z\",\n" +
                    "  \"manufacturer\": {\n" +
                    "    \"userId\": 1\n" +
                    "  },\n" +
                    "  \"maxSales\": 1000,\n" +
                    "  \"name\": \"product\",\n" +
                    "  \"productId\": 1\n" +
                    "}";
            MvcResult result = mockMvc.perform(put("/api/product/update/1")
                    .content(updateProductJSon)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/delete/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldNotBeAbleToDeleteProduct() throws Exception {
            MvcResult result = mockMvc.perform(delete("/api/product/delete/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isForbidden()).andReturn();
        }

        // /api/product/find/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldBeAbleToFindProduct() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/find/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findByCategory/{id}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldBeAbleToFindProductsByCategory() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findByCategory/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

        // /api/product/findProductsByName/{name}
        @WithMockUser(username = "raphael.chevasson@telecom-st-etienne.fr", authorities = {"SUPPLIER"})
        @Test
        void supplier_shouldBeAbleToFindProductsByName() throws Exception {
            MvcResult result = mockMvc.perform(get("/api/product/findProductsByName/pe")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk()).andReturn();
        }

    }

}