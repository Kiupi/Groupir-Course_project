package com.groupir.backend.repository;

import com.groupir.backend.model.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
}
