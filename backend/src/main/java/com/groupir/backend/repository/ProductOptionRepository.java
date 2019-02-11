package com.groupir.backend.repository;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.ProductOption;
import org.springframework.data.repository.CrudRepository;

public interface ProductOptionRepository extends CrudRepository<ProductOption, Long> {
    ProductOption findFirstByProduct(Product product);
}
