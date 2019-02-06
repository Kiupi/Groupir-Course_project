package com.groupir.backend.controller;

import com.groupir.backend.dao.AuthenticationRequest;
import com.groupir.backend.model.User;
import com.groupir.backend.security.JwtTokenProvider;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private ServiceOrderItem serviceOrderItem;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     *  the get request is "/api/user/list" to use this method
     * @return list of all user
     */
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users= serviceUser.findAllUser();
        if(users.size()==0){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

    }

    /**
     *  the post request is "/api/user/add" to use this method
     * @param newUser is a JSON of user
     * @return String
     */
    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody User newUser) {
       serviceUser.add(newUser);
       return new ResponseEntity<>("User added successfully",HttpStatus.OK);
    }

    /**
     *  the delete request is "/api/user/delete/{id}" to use this method
     * @param idUser is the variable "id" in the request
     * @return String
     */
    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int idUser){
        if(!serviceUser.findIfExist(idUser)){
            return new ResponseEntity<>("User with id "+idUser+" is not found", HttpStatus.NOT_FOUND);
        }
        serviceUser.delete(idUser);
        return new ResponseEntity<>("User with id "+ idUser+ " deleted", HttpStatus.OK);
    }

    /**
     *  the put request is "/api/user/update" to use this method
     * @param updateUser is a JSON of user
     * @param idUser is id of user
     * @return string
     */
    @PutMapping( value = "/update/{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser( @RequestBody User updateUser,@PathVariable(name = "id") int idUser){
        if(!serviceUser.findIfExist(idUser)){
            return new ResponseEntity<>("User with id "+idUser+" is not found", HttpStatus.NOT_FOUND);
        }
        updateUser.setUserId(idUser);
        serviceUser.update(updateUser);
        return new ResponseEntity<>("User with id "+idUser+" updated", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.serviceUser.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}