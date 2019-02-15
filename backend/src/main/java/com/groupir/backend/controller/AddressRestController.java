package com.groupir.backend.controller;

import com.groupir.backend.model.Address;
import com.groupir.backend.model.User;
import com.groupir.backend.service.AddressService;
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
@RequestMapping("/api/user/{user_id}/address")
public class AddressRestController {

    @Autowired
    private AddressService addressService;
    private ServiceUser userService;

    /**
     *  Handles the "/list" route
     * @param userId the id of the user, specified in the route
     * @return A ResponseEntity<String> response which contains (if userId exists)
     *         the list of all the addresses of the user given his id
     */
    @GetMapping("/list")
    public ResponseEntity<List<Address>> getAllAdress(@PathVariable(name = "user_id") int userId) {
        List<Address> userAddresses = addressService.findAllFromUserId(userId);
        return new ResponseEntity<>(userAddresses, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/address/add" route
     * @param newAddress the new address, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAddress(@RequestBody Address newAddress,
                                             @PathVariable(name = "user_id") int userId) {
        if (userId != newAddress.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and address.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        long addressId = addressService.add(newAddress);
        return new ResponseEntity<>("Address added successfully - generated id is " + addressId, HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/address/delete/{address_id}" route
     * @param userId the id of the user, specified in the route
     * @param addressId the id of the address, specified in the route
     * @return A ResponseEntity<String> response
     */
    @DeleteMapping( "/delete/{address_id}")
    public ResponseEntity<String> deleteAddress(@PathVariable(name = "address_id") long addressId,
                                                @PathVariable(name = "user_id") int userId) {
        Optional<Address> potentialAddress = addressService.findById(addressId);
        if (!potentialAddress.isPresent()) {
            return new ResponseEntity<>("Address with id " + addressId + " not found",
                    HttpStatus.NOT_FOUND);
        }
        Address address = potentialAddress.get();
        if (userId != address.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and address.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        addressService.delete(addressId);
        return new ResponseEntity<>("Address with id " + addressId + " deleted successfully",
                HttpStatus.OK);
    }

    /**
     *  Handles the "/api/user/{user_id}/address/update/{address_id}" route
     * @param userId the id of the user, specified in the route
     * @param addressId the id of the address, specified in the route
     * @param updatedAddress the new address state, specified in the json request body
     * @return A ResponseEntity<String> response
     */
    @PutMapping( value = "/update/{address_id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable(name = "address_id") long addressId,
                                     @PathVariable(name = "user_id") int userId,
                                     @RequestBody Address updatedAddress) {
        if (userId != updatedAddress.getUser().getUserId()) {
            return new ResponseEntity<>("User id from url and address.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        if (updatedAddress.getAddressId() != null && addressId != updatedAddress.getAddressId()) {
            return new ResponseEntity<>("Adress id from url and address.user.id from request body mismatch",
                    HttpStatus.CONFLICT);
        }
        updatedAddress.setAddressId(addressId);

        if(!addressService.isPresent(addressId)){
            return new ResponseEntity<>("Address with id " + addressId + " not found", HttpStatus.NOT_FOUND);
        }
        addressService.update(updatedAddress);
        return new ResponseEntity<>("Address with id " + addressId + " updated successfully", HttpStatus.OK);
    }

}