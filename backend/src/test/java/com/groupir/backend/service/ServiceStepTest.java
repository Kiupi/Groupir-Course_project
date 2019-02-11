package com.groupir.backend.service;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.Step;
import com.groupir.backend.repository.StepRepository;
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
class ServiceStepTest {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceStep serviceStep;

    Step step;
    long stepID;

    @BeforeEach
    public void createStep() {
        Product product = new Product();
        product.setProductId((long) 1);
        step = new Step();
        step.setProduct(product);
        step.setThreshold(100);
        step = stepRepository.save(step);
        stepID = step.getStepId();
    }

    @AfterEach
    public void deleteStep() {
        if (stepRepository.findById(stepID).isPresent()) {
            stepRepository.delete(step);
        }
    }

    @Test
    void shouldGetAllSteps() {
        Assert.assertEquals(stepRepository.findAll(), serviceStep.findAllStep());
    }

    @Test
    void shouldGetOneStep() {
        Assert.assertEquals(stepRepository.findById(stepID).get(), serviceStep.findOne(stepID));
    }

    @Test
    void shouldAddStep() {
        Product product = new Product();
        product.setProductId((long) 1);
        Step step = new Step();
        step.setProduct(product);
        step.setThreshold(100);
        Step returnedStep = serviceStep.add(step);
        assertTrue(stepRepository.findById((long) returnedStep.getStepId()).isPresent());
        stepRepository.delete(returnedStep);
    }

    @Test
    void shouldUpdateStep() {
        int thresholdExpected = 400;
        assert step != null;
        Step stepUpdated = stepRepository.findById(stepID).get();
        stepUpdated.setThreshold(thresholdExpected);
        serviceStep.update(stepUpdated);
        step = stepRepository.findById(stepID).get();
        Assert.assertTrue(thresholdExpected == step.getThreshold());
    }

    @Test
    void shouldDeleteStep() {
        Assert.assertTrue(stepRepository.findById(stepID).isPresent());
        serviceStep.delete(stepID);
        Assert.assertFalse(stepRepository.findById(stepID).isPresent());
    }

    @Test
    void shouldReturnPresent() {
        assertTrue(serviceStep.isPresent(stepID));
        stepRepository.delete(step);
        assertFalse(serviceStep.isPresent(stepID));
    }
}