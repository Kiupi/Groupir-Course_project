package com.groupir.backend.controller;

import com.groupir.backend.model.PaymentMethod;
import com.groupir.backend.service.PaymentMethodService;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/user/{user_id}/payment-method")
public class PaymentMethodRestController {

    @Autowired
    private PaymentMethodService paymentMethodService;
    private ServiceUser userService;

    /**
     *  Handles the "/list" route
     * @param userId the id of the user, specified in the route
     * @return A ResponseEntity<String> response which contains (if userId exists)
     *         the list of all the paymentMethods of the user given his id
     */
    @GetMapping("/list")
    public ResponseEntity<List<PaymentMethod>> getAllAdress(@PathVariable(name = "user_id") int userId) {
        List<PaymentMethod> userPaymentMethods = paymentMethodService.findAllFromUserId(userId);
        return new ResponseEntity<>(userPaymentMethods, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/payment-method/add" route
     * @param newPaymentMethod the new paymentMethod, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethod newPaymentMethod,
                                             @PathVariable(name = "user_id") int userId) {
        if (userId != newPaymentMethod.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and paymentMethod.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        long paymentMethodId = paymentMethodService.add(newPaymentMethod);
        return new ResponseEntity<>("PaymentMethod added successfully - generated id is " + paymentMethodId, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/payment-method/delete/{paymentMethod_id}" route
     * @param userId the id of the user, specified in the route
     * @param paymentMethodId the id of the paymentMethod, specified in the route
     * @return A ResponseEntity<String> response
     */
    @DeleteMapping( "/delete/{paymentMethod_id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable(name = "paymentMethod_id") long paymentMethodId,
                                                @PathVariable(name = "user_id") int userId) {
        Optional<PaymentMethod> potentialPaymentMethod = paymentMethodService.findById(paymentMethodId);
        if (!potentialPaymentMethod.isPresent()) {
            return new ResponseEntity<>("PaymentMethod with id " + paymentMethodId + " not found",
                    HttpStatus.NOT_FOUND);
        }
        PaymentMethod paymentMethod = potentialPaymentMethod.get();
        if (userId != paymentMethod.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and paymentMethod.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        paymentMethodService.delete(paymentMethodId);
        return new ResponseEntity<>("PaymentMethod with id " + paymentMethodId + " deleted successfully",
                HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/payment-method/update/{paymentMethod_id}" route
     * @param userId the id of the user, specified in the route
     * @param paymentMethodId the id of the paymentMethod, specified in the route
     * @param updatedPaymentMethod the new paymentMethod state, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PutMapping( value = "/update/{paymentMethod_id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable(name = "paymentMethod_id") long paymentMethodId,
                                     @PathVariable(name = "user_id") int userId,
                                     @RequestBody PaymentMethod updatedPaymentMethod) {
        if (userId != updatedPaymentMethod.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and paymentMethod.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        if (updatedPaymentMethod.getPaymentMethodId() != null && paymentMethodId != updatedPaymentMethod.getPaymentMethodId()) {
            return new ResponseEntity<>("Adress id from url and paymentMethod.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        updatedPaymentMethod.setPaymentMethodId(paymentMethodId);

        if(!paymentMethodService.isPresent(paymentMethodId)){
            return new ResponseEntity<>("PaymentMethod with id " + paymentMethodId + " not found", HttpStatus.NOT_FOUND);
        }
        paymentMethodService.update(updatedPaymentMethod);
        return new ResponseEntity<>("PaymentMethod with id " + paymentMethodId + " updated successfully", HttpStatus.OK);
    }

}