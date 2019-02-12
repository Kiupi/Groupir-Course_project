package com.groupir.backend.service;

import com.groupir.backend.exceptions.PriceConflictException;
import com.groupir.backend.exceptions.PriceNotFoundException;
import com.groupir.backend.exceptions.ProductOptionNotFoundException;
import com.groupir.backend.exceptions.StepNotFoundException;
import com.groupir.backend.model.Price;
import com.groupir.backend.model.PriceKey;
import com.groupir.backend.model.ProductOption;
import com.groupir.backend.model.Step;
import com.groupir.backend.repository.PriceRepository;
import com.groupir.backend.repository.ProductOptionRepository;
import com.groupir.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePrice {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    /**
     *  add a new price
     * @param price the new price
     * @return price added
     */
    public Price add(Price price) {
        if(priceRepository.findById(price.getKey()).isPresent()){
            throw new PriceConflictException();
        }
        return priceRepository.save(price);
    }

    /**
     *  delete a price
     * @param idOption
     * @param idStep
     */
    public void delete(long idOption, long idStep) {
        priceRepository.delete(getPrice(idOption,idStep));
    }

    /**
     * update a price
     * @param idOption
     * @param idStep
     * @param price
     * @return price updated
     */
    public Price update(long idOption, long idStep,Price price) {
        if(getPrice(idOption,idStep)!= null){
           return  priceRepository.save(price);
        }
        return null;
    }

    /**
     *  get a price
     * @param idOption
     * @param idStep
     * @return
     */
    public Price getPrice(long idOption, long idStep){
        Step step= stepRepository.findById(idStep)
                .orElseThrow( () -> new StepNotFoundException("The step id "+idStep + " not found!"));
        ProductOption productOption = productOptionRepository.findById(idOption)
                .orElseThrow(() -> new ProductOptionNotFoundException("the ProductOption id "+ idOption+ " not found!"));
        PriceKey priceKey = new PriceKey();
        priceKey.setOption(productOption);
        priceKey.setStep(step);
        return priceRepository.findById(priceKey).orElseThrow(() -> new PriceNotFoundException("the price is not found !"));
    }
}
