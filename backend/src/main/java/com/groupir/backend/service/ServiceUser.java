package com.groupir.backend.service;

import com.groupir.backend.model.User;
import com.groupir.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@Transactional
public class ServiceUser {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAllUser(){
        return (List<User>) userRepository.findAll();
    }

    public void add(User newUser) {
        userRepository.save(newUser);
    }

    public void delete(int idUser) {
        userRepository.deleteById(idUser);
    }

    public void update( User updateUser) {
        userRepository.save(updateUser);
    }
}
