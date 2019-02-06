package com.groupir.backend.repository;

import com.groupir.backend.model.Step;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StepRepository extends CrudRepository<Step, Long> {
    /**
     *  return the step of the product in function the somme of product ordered
     * @param product_productId product's id
     * @param threshold somme of product ordered
     * @return Step
     */
    Step findByProduct_ProductIdAndThresholdLessThanEqualOrderByThresholdDesc(Long product_productId, Integer threshold);
}
