package com.groupir.backend.controller;

import com.groupir.backend.model.User;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/list")
    public @ResponseBody
    ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(serviceUser.findAllUser(), HttpStatus.OK);
    }
    /*
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User newUser){
        return new ResponseEntity(serviceUser.add(newUser));
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") int idUser){
        return new ResponseEntity(serviceUser.delete(idUser));
    }

    @RequestMapping(path = "/update",method = RequestMethod.PATCH)
    public ResponseEntity updateUser(@RequestBody User updateUser){
        return new ResponseEntity(serviceUser.update(updateUser));
    }*/

}