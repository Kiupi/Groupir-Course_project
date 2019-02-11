package com.groupir.backend.service;

import com.groupir.backend.model.Address;
import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("/sql/groupir_address.sql") // use a new h2 base for test, filled by this SQL file
@ComponentScan("com.groupir.backend.service")
class ServiceAddressTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ServiceUser userService;

    @Test
    void shouldGetAllAddresses(){
        List<Address> allAddress = addressService.findAll();
        System.out.println("allAddress = " + allAddress);

        // assert all addresses are returned
        Assert.assertEquals(6, allAddress.size());

        // assert some address values
        Assert.assertEquals("France", addressService.findById(1L).get().getCountry());
        Assert.assertEquals("Saint Étienne", addressService.findById(1L).get().getCity());
        Assert.assertEquals(6, (long)addressService.findById(6L).get().getAddressId());
    }

    @Test
    void shouldAddAddress(){
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
        user.setUserId(1);

        Address address = new Address();
        address.setUser(user);
        address.setCity("TestCity");
        address.setCountry("TestCountry");
        address.setNumber("42");
        address.setPostalCode("42000");
        address.setStreet("TestStreet");

        long newAddressId = addressService.add(address);
        assertTrue(addressService.findById(newAddressId).isPresent());
        Assert.assertEquals("TestCity", addressService.findById(newAddressId).get().getCity());
    }

    @Test
    void shouldUpdateUser(){
        long id = 1;
        Address address = addressService.findById(id).get();
        address.setStreet("updatedStreet");
        addressService.update(address);
        Assert.assertEquals("updatedStreet", addressService.findById(id).get().getStreet());
    }

    @Test
    void shouldDeleteUser(){
        long id = 1;
        Assert.assertTrue(addressService.findById(id).isPresent());
        addressService.delete(id);
        Assert.assertFalse(addressService.findById(id).isPresent());
    }
}