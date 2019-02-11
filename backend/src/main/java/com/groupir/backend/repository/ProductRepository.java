package com.groupir.backend.repository;

import com.groupir.backend.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByCategory_CategoryId(Integer category_categoryId);
    List<Product> findAllByNameContaining(String name);
}
