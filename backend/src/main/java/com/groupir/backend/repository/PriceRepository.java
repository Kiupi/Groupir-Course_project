package com.groupir.backend.repository;

import com.groupir.backend.model.Price;
import com.groupir.backend.model.PriceKey;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, PriceKey> {
}
