package com.groupir.backend.repository;

import com.groupir.backend.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
