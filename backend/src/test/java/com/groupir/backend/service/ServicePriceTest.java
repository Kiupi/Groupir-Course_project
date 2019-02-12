package com.groupir.backend.service;

import com.groupir.backend.model.*;
import com.groupir.backend.repository.PriceRepository;
import com.groupir.backend.repository.ProductOptionRepository;
import com.groupir.backend.repository.StepRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("/sql/groupir_address.sql")
@ComponentScan("com.groupir.backend.service")
class ServicePriceTest {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ServicePrice servicePrice;

    @Test
    void shouldAddPrice() {
        Price price = new Price();
        PriceKey priceKey = new PriceKey();
        Step step = new Step();
        ProductOption productOption = new ProductOption();
        Product product = new Product();
        product.setProductId(6L);
        step.setProduct(product);
        step.setThreshold(2000);
        step=stepRepository.save(step);
        productOption.setOptionId(12L);
        priceKey.setStep(step);
        priceKey.setOption(productOption);
        price.setKey(priceKey);
        price.setPrice(new BigDecimal(255));
        servicePrice.add(price);
        Assert.assertTrue(priceRepository.findById(priceKey).isPresent());
    }

    @Test
    void shouldDeletePrice() {
        final long OPTION_ID=1L;
        final long STEP_ID=2L;
        PriceKey priceKey = new PriceKey();
        Step step = new Step();
        ProductOption productOption = new ProductOption();
        step.setStepId(STEP_ID);
        productOption.setOptionId(OPTION_ID);
        priceKey.setOption(productOption);
        priceKey.setStep(step);
        assertTrue(priceRepository.findById(priceKey).isPresent());
        servicePrice.delete(OPTION_ID,STEP_ID);
        assertFalse(priceRepository.findById(priceKey).isPresent());

    }

    @Test
    void shouldUpdatePrice() {
        final long OPTION_ID=1L;
        final long STEP_ID=2L;
        PriceKey priceKey = new PriceKey();
        Step step = new Step();
        ProductOption productOption = new ProductOption();
        step.setStepId(STEP_ID);
        productOption.setOptionId(OPTION_ID);
        priceKey.setOption(productOption);
        priceKey.setStep(step);
        Price price=priceRepository.findById(priceKey).get();
        BigDecimal newPrice= new BigDecimal( 1000000);
        BigDecimal oldPrice=price.getPrice();
        price.setPrice(newPrice);
        price=servicePrice.update(OPTION_ID,STEP_ID,price);
        assertNotEquals(oldPrice,price.getPrice());

    }

    @Test
    void shouldGetPrice() {
        final long OPTION_ID=1L;
        final long STEP_ID=2L;
        Price price= servicePrice.getPrice(OPTION_ID,STEP_ID);
        assertNotNull(price);
    }
}