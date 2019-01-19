package com.groupir.backend.controller;

import com.groupir.backend.model.User;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(serviceUser.findAllUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody User newUser) {
       serviceUser.add(newUser);
       return new ResponseEntity<>("User added successfully",HttpStatus.OK);
    }

    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int idUser){
        serviceUser.delete(idUser);
        return new ResponseEntity<>("User "+ idUser+ " deleted", HttpStatus.OK);
    }

    @PutMapping( value = "/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody User updateUser){
        serviceUser.update(updateUser);
        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }

}