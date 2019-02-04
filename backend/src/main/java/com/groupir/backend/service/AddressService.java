package com.groupir.backend.service;

import com.groupir.backend.model.User;
import com.groupir.backend.model.Address;
import com.groupir.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     *  Return all the addresses
     * @return list of all the addresses
     */
    public List<Address> findAll(){
        return (List<Address>) addressRepository.findAll();
    }

    /**
     *  Return all the addresses of a given user
     * @param userId the user id
     * @return list of all the addresses of the user
     */
    public List<Address> findAllFromUserId(int userId){
        return findAll().stream()
                        .filter(a -> userId == a.getUser().getUserId())
                        .collect(Collectors.toList());
    }

    /**
     *  Create a new address
     * @param newAddress the address
     */
    public void add(Address newAddress){
        addressRepository.save(newAddress);
    }

    /**
     *  Delete the address given its id
     * @param addressId the address id
     */
    public void delete(long addressId){
        addressRepository.deleteById(addressId);
    }

    /**
     *  Modify an address
     * @param address the new
     */
    public void update(Address address){
        addressRepository.save(address);
    }

    /**
     *  Get the address with a given id
     * @param addressID the address id
     * @return the address
     */
    public Optional<Address> findById(long addressID){
        return addressRepository.findById(addressID);
    }

    /**
     *  Check if the address with a given id is in the database
     * @param addressID the address id
     * @return true if the address is in the database, else false
     */
    public boolean isPresent(long addressID){
        return addressRepository.findById(addressID).isPresent();
    }

    // TODO: choose default address
}