package com.groupir.backend.service;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.Category;
import com.groupir.backend.repository.CategoryRepository;
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
class ServiceCategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceCategory serviceCategory;

    Category category;
    int categoryID;

    @BeforeEach
    public void createCategory() {
        category = new Category();
        category.setName("categoryTest");
        category = categoryRepository.save(category);
        categoryID = category.getCategoryId();
    }

    @AfterEach
    public void deleteCategory() {
        if (categoryRepository.findById(categoryID).isPresent()) {
            categoryRepository.delete(category);
        }
    }

    @Test
    void shouldGetAllCategories() {
        Assert.assertEquals(categoryRepository.findAll(), serviceCategory.findAllCategory());
    }

    @Test
    void shouldGetAllCategoryIds() {
        assertEquals(categoryRepository.getAllIds(), serviceCategory.findAllCategoryIds());
    }

    @Test
    void shouldGetOneCategory() {
        Assert.assertEquals(categoryRepository.findById(categoryID).get(), serviceCategory.findOne(categoryID));
    }

    @Test
    void shouldAddCategory() {
        Product product = new Product();
        product.setProductId((long) 1);
        Category category = new Category();
        category.setName("categoryTest");
        Category returnedCategory = serviceCategory.add(category);
        assertTrue(categoryRepository.findById(returnedCategory.getCategoryId()).isPresent());
        categoryRepository.delete(returnedCategory);
    }

    @Test
    void shouldUpdateCategory() {
        String nameExpected = "Toto";
        assert category != null;
        Category categoryUpdated = categoryRepository.findById(categoryID).get();
        categoryUpdated.setName(nameExpected);
        serviceCategory.update(categoryUpdated);
        category = categoryRepository.findById(categoryID).get();
        Assert.assertTrue(nameExpected.equals(category.getName()));
    }

    @Test
    void shouldDeleteCategory() {
        Assert.assertTrue(categoryRepository.findById(categoryID).isPresent());
        serviceCategory.delete(categoryID);
        Assert.assertFalse(categoryRepository.findById(categoryID).isPresent());
    }

    @Test
    void shouldReturnPresent() {
        assertTrue(serviceCategory.isPresent(categoryID));
        categoryRepository.delete(category);
        assertFalse(serviceCategory.isPresent(categoryID));
    }
}