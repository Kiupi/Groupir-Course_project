package com.groupir.backend.repository;

import com.groupir.backend.model.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query(value = "select category_id from category", nativeQuery = true)
    List<Integer> getAllIds();
}
