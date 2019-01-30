package com.groupir.backend.controller;

import com.groupir.backend.service.ServiceProduct;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductRestController.class,secure = false)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceProduct serviceProduct;

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

    private String exampleProductAddJson = "{\"manufacturer\": \"Lego\",\n" +
            "  \"category\": null \n"+
            "  \"name\": \"train\",\n" +
            "  \"description\": \"Bonjour bonjour\",\n" +
            "  \"endDate\": \"2019-06-05\",\n" +
            "  \"maxSale\": null\n" +
            "  },\n" +
            "}";

    @Test
    void shouldCode200ForGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/product/list");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }
}