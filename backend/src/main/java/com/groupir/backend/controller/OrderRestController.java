package com.groupir.backend.controller;

import com.groupir.backend.model.Order;
import com.groupir.backend.service.OrderService;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/api/user/{user_id}/order")
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    private ServiceUser userService;

    /**
     *  Handles the "/list" route
     * @param userId the id of the user, specified in the route
     * @return A ResponseEntity<String> response which contains (if userId exists)
     *         the list of all the orderes of the user given his id
     */
    @GetMapping("/list")
    public ResponseEntity<List<Order>> getAllAdress(@PathVariable(name = "user_id") int userId) {
        List<Order> userOrderes = orderService.findAllFromUserId(userId);
        return new ResponseEntity<>(userOrderes, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/order/add" route
     * @param newOrder the new order, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrder(@RequestBody Order newOrder,
                                             @PathVariable(name = "user_id") int userId) {
        if (userId != newOrder.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and order.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        long orderId = orderService.add(newOrder);
        return new ResponseEntity<>("Order added successfully - generated id is " + orderId, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/order/delete/{order_id}" route
     * @param userId the id of the user, specified in the route
     * @param orderId the id of the order, specified in the route
     * @return A ResponseEntity<String> response
     */
    @DeleteMapping( "/delete/{order_id}")
    public ResponseEntity<String> deleteOrder(@PathVariable(name = "order_id") long orderId,
                                                @PathVariable(name = "user_id") int userId) {
        Optional<Order> potentialOrder = orderService.findById(orderId);
        if (!potentialOrder.isPresent()) {
            return new ResponseEntity<>("Order with id " + orderId + " not found",
                    HttpStatus.NOT_FOUND);
        }
        Order order = potentialOrder.get();
        if (userId != order.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and order.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        orderService.delete(orderId);
        return new ResponseEntity<>("Order with id " + orderId + " deleted successfully",
                HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/order/update/{order_id}" route
     * @param userId the id of the user, specified in the route
     * @param orderId the id of the order, specified in the route
     * @param updatedOrder the new order state, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PutMapping( value = "/update/{order_id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable(name = "order_id") long orderId,
                                     @PathVariable(name = "user_id") int userId,
                                     @RequestBody Order updatedOrder) {
        if (userId != updatedOrder.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and order.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        if (updatedOrder.getOrderId() != null && orderId != updatedOrder.getOrderId()) {
            return new ResponseEntity<>("Adress id from url and order.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        updatedOrder.setOrderId(orderId);

        if(!orderService.isPresent(orderId)){
            return new ResponseEntity<>("Order with id " + orderId + " not found", HttpStatus.NOT_FOUND);
        }
        orderService.update(updatedOrder);
        return new ResponseEntity<>("Order with id " + orderId + " updated successfully", HttpStatus.OK);
    }

}