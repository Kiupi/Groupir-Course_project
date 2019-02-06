package com.groupir.backend.repository;

import com.groupir.backend.model.Price;
import com.groupir.backend.model.PriceKey;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, PriceKey> {

    /**
     *  return the product's price in function of step and option
     * @param key_option_optionId option's id
     * @param key_step_stepId step's id
     * @return price
     */
    Price findByKey_Option_OptionIdAndKey_Step_StepId(Long key_option_optionId, Long key_step_stepId);
}
