package com.groupir.backend.service;

import com.groupir.backend.model.ProductOption;
import com.groupir.backend.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ServiceProductOption {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    /**
     * find all productOption from database
     *
     * @return list of all productOption
     */
    public List<ProductOption> findAllProductOption() {
        return (List<ProductOption>) productOptionRepository.findAll();
    }

    /**
     * find one productOption from database
     *
     * @return one productOption
     */
    public ProductOption findOne(long idProductOption) {
        try {
            return productOptionRepository.findById(idProductOption).get();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    /**
     * Add a productOption in database
     *
     * @param newProductOption is a productOption
     */
    public void add(ProductOption newProductOption) {
        productOptionRepository.save(newProductOption);
    }

    /**
     * Delete a productOption in database
     *
     * @param idProductOption
     */
    public void delete(long idProductOption) {
        productOptionRepository.deleteById(idProductOption);
    }

    /**
     * update a productOption drom database
     *
     * @param updateProductOption is a productOption
     */
    public void update(ProductOption updateProductOption) {
        productOptionRepository.save(updateProductOption);
    }

    /**
     * check the id of productOption in database
     *
     * @param idProductOption is id of productOption
     * @return false if isn't present in database else false
     */
    public boolean isPresent(long idProductOption) {
        return productOptionRepository.findById(idProductOption).isPresent();
    }
}
