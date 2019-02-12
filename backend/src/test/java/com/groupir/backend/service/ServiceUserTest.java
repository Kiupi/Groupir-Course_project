package com.groupir.backend.service;

import com.google.common.collect.Lists;
import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("/sql/groupir_address.sql")
@ComponentScan("com.groupir.backend.service")
class ServiceUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceUser serviceUser;

    @Test
    void shouldGetAllUsers(){
        Assert.assertEquals(userRepository.findAll(),serviceUser.findAllUser());
    }

    @Test
    void shouldAddUser(){
        Role role = new Role();
        role.setRoleId(1);
        User  user = new User();
        user.setPassword("1234");
        user.encodePassword(passwordEncoder);
        user.setLastName("test");
        user.setFirstName("testTest");
        user.setEmail("test@test.fr");
        user.setBirthDate(new Date());
        user.setRole(role);
        user.setEnabled(true);
        serviceUser.add(user);
        assertTrue(userRepository.findByEmail("test@test.fr").isPresent());
    }

    @Test
    void shouldUpdateUser(){
        User user=null;
        if(userRepository.findById(1).isPresent()){
            user=userRepository.findById(1).orElse(null);
        }else{
            fail("user is not present");
        }
        String firstNameExpected = "Jean";
        assert user != null;
        user.setFirstName(firstNameExpected);
        User userUpdate=serviceUser.update(user);
        Assert.assertEquals(firstNameExpected,userUpdate.getFirstName());
    }

    @Test
    void shouldDeleteUser(){
        Assert.assertTrue(userRepository.findById(1).isPresent());
        serviceUser.delete(1);
        Assert.assertFalse(userRepository.findById(1).isPresent());
    }
}