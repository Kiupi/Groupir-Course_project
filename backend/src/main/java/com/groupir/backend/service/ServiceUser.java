package com.groupir.backend.service;

import com.groupir.backend.model.User;
import com.groupir.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *  find all user from database
     * @return list of all user
     */
    public List<User> findAllUser(){
        return (List<User>) userRepository.findAll();
    }

    /**
     * find user by its email
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     *  Add a user in database
     * @param newUser is a user
     */
    public User add(User newUser) {
        newUser.encodePassword(passwordEncoder);
        userRepository.save(newUser);
        return newUser;
    }

    /**
     *  Delete a user in database
     * @param idUser
     */
    public void delete(int idUser) {
        userRepository.deleteById(idUser);
    }

    /**
     *  update a user from database
     * @param updateUser is a user
     */
    public User update(User updateUser) {
        Optional<User> inDB = userRepository.findById(updateUser.getUserId());
        if(updateUser.getPassword() != null){
            updateUser.encodePassword(passwordEncoder);
        }
        else {
            updateUser.setPassword(inDB.get().getPassword());
        }
        userRepository.save(updateUser);

        return updateUser;
    }

    /**
     *  check the id of user in database
     * @param idUser is id of user
     * @return false if isn't present in database else false
     */
    public boolean findIfExist(int idUser) {
        return userRepository.findById(idUser).isPresent();
    }

    /**
     * find user by its id
     * @param idUser user's id
     * @return User
     */
    public Optional<User> findById(int idUser) {
        return userRepository.findById(idUser);
    }
}
