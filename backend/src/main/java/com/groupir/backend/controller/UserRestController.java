package com.groupir.backend.controller;

import com.groupir.backend.dto.AuthenticationRequest;
import com.groupir.backend.dto.OrderDTO;
import com.groupir.backend.exceptions.InvalidJwtAuthenticationException;
import com.groupir.backend.exceptions.NoRoleSetException;
import com.groupir.backend.exceptions.OperationNotAllowedException;
import com.groupir.backend.exceptions.UserNotFoundException;
import com.groupir.backend.model.Order;
import com.groupir.backend.model.Role;
import com.groupir.backend.model.User;
import com.groupir.backend.security.JwtTokenProvider;
import com.groupir.backend.service.ServiceOrderItem;
import com.groupir.backend.service.ServiceUser;
import io.swagger.annotations.ApiKeyAuthDefinition;
import com.groupir.backend.service.ServiceUserOrders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
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

    @Autowired
    private ServiceUserOrders serviceUserOrders;

    /**
     * the get request is "/api/user/list" to use this method
     *
     * @return list of all user
     */

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = serviceUser.findAllUser();
        if (users.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/user/{id}" to use this method
     *
     * @return one user
     */
    @GetMapping("/get/{id}")
    public ResponseEntity getOneUser(@PathVariable("id") int idUser) {
        User user = serviceUser.findOne(idUser);
        if (user == null) {
            return new ResponseEntity<>("User with id " + idUser + " not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

    }

    /**
     * the post request is "/api/user/add" to use this method
     *
     * @param newUser is a JSON of user
     * @return String
     */
    @PostMapping(value = "/add", produces = APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User newUser, Authentication authentication) {
        if (newUser.getRole() == null) {
            throw new NoRoleSetException();
        }
        if ((authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN))) && !newUser.getRole().getRoleId().equals(2)) {
            throw new OperationNotAllowedException("You can only create user, not supplier or admin");
        }
        return serviceUser.add(newUser);
    }

    /**
     * the delete request is "/api/user/delete/{id}" to use this method
     *
     * @param idUser is the variable "id" in the request
     * @return String
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int idUser, Authentication authentication) {
        Optional<User> oAuthenticated = serviceUser.findByEmail((String) authentication.getName());
        if (!oAuthenticated.isPresent()) {
            throw new UserNotFoundException("Authenticated user could not be found in db");
        }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN)) && oAuthenticated.get().getUserId().equals(idUser)) {
            throw new OperationNotAllowedException(Role.ADMIN + " users cannot delete themselves. Please ask an other " + Role.ADMIN + " to do so");
        }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.USER)) && !oAuthenticated.get().getUserId().equals(idUser)) {
            throw new OperationNotAllowedException("Users cannot delete other users");
        }

        if (!serviceUser.findIfExist(idUser)) {
            return new ResponseEntity<>("User with id " + idUser + " is not found", HttpStatus.NOT_FOUND);
        }
        serviceUser.delete(idUser);
        return new ResponseEntity<>("User with id " + idUser + " deleted", HttpStatus.OK);
    }

    /**
     * the put request is "/api/user/update" to use this method
     *
     * @param updateUser is a JSON of user
     * @param idUser     is id of user
     * @return string
     */
    @PutMapping(value = "/update/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody User updateUser, @PathVariable(name = "id") int idUser, Authentication authentication) {

        Optional<User> oUser = this.serviceUser.findByEmail((String) authentication.getName());
        if (!oUser.isPresent()) {
            throw new UserNotFoundException("Authenticated user could not be found in db");
        }
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN)) && oUser.get().getUserId() != idUser) {
            throw new OperationNotAllowedException("You cannot change an other user details");
        }

        if (!serviceUser.findIfExist(idUser)) {
            return new ResponseEntity<>("User with id " + idUser + " is not found", HttpStatus.NOT_FOUND);
        }
        updateUser.setUserId(idUser);
        serviceUser.update(updateUser);
        return new ResponseEntity<>("User with id " + idUser + " updated", HttpStatus.OK);
    }

    /**
     * the put request is "/api/user/currentUser" to use this method
     *
     * @return string
     */
    @GetMapping(value = "/currentUser", produces = APPLICATION_JSON_VALUE)
    public User getCurrentUser(Authentication authentication) {
        Optional<User> oUser = serviceUser.findByEmail(authentication.getName());
        if(!oUser.isPresent()){
            throw new UserNotFoundException("User corresponding to authentication not found");
        }
        return oUser.get();
    }

    @ApiKeyAuthDefinition(name = "authToken", key = "Authorization", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
    @PostMapping("/login")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.serviceUser.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "  found")).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    /**
     * the get request is "/api/user/history_purchase/{id}" to use this method
     *
     * @param idUser is user's id
     * @return order's list of user
     */
    @GetMapping(value = "/history_purchase/{id}")
    public ResponseEntity historyPurchase(@PathVariable(name = "id") int idUser) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        ModelMapper modelMapperOrder = new ModelMapper();
        modelMapperOrder.createTypeMap(Order.class, OrderDTO.class)
                .addMappings(mapping -> {
                    mapping.map(Order::getOrderId, OrderDTO::setId);
                    mapping.map(Order::getAddress, OrderDTO::setAddress);
                    mapping.map(Order::getOrderDate, OrderDTO::setDateOrder);
                });

        User user = serviceUser.isPresent(idUser).orElseThrow(() -> new UserNotFoundException("Not found the user id " + idUser));
        List<Order> orderList = serviceUserOrders.getListOrderByUser(user);

        orderList.forEach(order -> {
            OrderDTO orderDTO = new OrderDTO();
            modelMapperOrder.map(order, orderDTO);

            orderDTO.setOrderItems(serviceOrderItem.getProducts(order.getOrderId()));
            orderDTOS.add(orderDTO);
        });
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

}