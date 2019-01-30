package com.groupir.backend.service;

import com.groupir.backend.model.User;
import com.groupir.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceUser {

    @Autowired
    private UserRepository userRepository;

    /**
     *  find all user from database
     * @return list of all user
     */
    public List<User> findAllUser(){
        return (List<User>) userRepository.findAll();
    }

    /**
     *  Add a user in database
     * @param newUser is a user
     */
    public void add(User newUser) {
        userRepository.save(newUser);
    }

    /**
     *  Delete a user in database
     * @param idUser
     */
    public void delete(int idUser) {
        userRepository.deleteById(idUser);
    }

    /**
     *  update a user drom database
     * @param updateUser is a user
     */
    public void update( User updateUser) {
        userRepository.save(updateUser);
    }

    /**
     *  check the id of user in database
     * @param idUser is id of user
     * @return false if isn't present in database else false
     */
    public boolean findIfExist(int idUser) {
        return userRepository.findById(idUser).isPresent();
    }
}
