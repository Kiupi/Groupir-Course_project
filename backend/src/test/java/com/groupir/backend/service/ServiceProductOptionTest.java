package com.groupir.backend.service;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.ProductOption;
import com.groupir.backend.repository.ProductOptionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("/sql/groupir_address.sql")
@ComponentScan("com.groupir.backend.service")
class ServiceProductOptionTest {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceProductOption serviceProductOption;

    ProductOption productOption;
    long productOptionID;

    @BeforeEach
    public void createProductOption() {
        Product product = new Product();
        product.setProductId((long) 1);
        productOption = new ProductOption();
        productOption.setProduct(product);
        productOption.setOptionName("optionTest");
        productOption = productOptionRepository.save(productOption);
        productOptionID = productOption.getOptionId();
    }

    @AfterEach
    public void deleteProductOption() {
        if (productOptionRepository.findById(productOptionID).isPresent()) {
            productOptionRepository.delete(productOption);
        }
    }

    @Test
    void shouldGetAllProductOptions() {
        Assert.assertEquals(productOptionRepository.findAll(), serviceProductOption.findAllProductOption());
    }

    @Test
    void shouldGetOneProductOption() {
        Assert.assertEquals(productOptionRepository.findById(productOptionID).get(), serviceProductOption.findOne(productOptionID));
    }

    @Test
    void shouldAddProductOption() {
        Product product = new Product();
        product.setProductId((long) 1);
        ProductOption productOption = new ProductOption();
        productOption.setProduct(product);
        productOption.setOptionName("optionTest");
        ProductOption returnedProductOption = serviceProductOption.add(productOption);
        assertTrue(productOptionRepository.findById((long) returnedProductOption.getOptionId()).isPresent());
        productOptionRepository.delete(returnedProductOption);
    }

    @Test
    void shouldUpdateProductOption() {
        String optionExpected = "Toto";
        assert productOption != null;
        ProductOption productOptionUpdated = productOptionRepository.findById(productOptionID).get();
        productOptionUpdated.setOptionName(optionExpected);
        serviceProductOption.update(productOptionUpdated);
        productOption = productOptionRepository.findById(productOptionID).get();
        Assert.assertTrue(optionExpected.equals(productOption.getOptionName()));
    }

    @Test
    void shouldDeleteProductOption() {
        Assert.assertTrue(productOptionRepository.findById(productOptionID).isPresent());
        serviceProductOption.delete(productOptionID);
        Assert.assertFalse(productOptionRepository.findById(productOptionID).isPresent());
    }

    @Test
    void shouldReturnPresent() {
        assertTrue(serviceProductOption.isPresent(productOptionID));
        productOptionRepository.delete(productOption);
        assertFalse(serviceProductOption.isPresent(productOptionID));
    }
}