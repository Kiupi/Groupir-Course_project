package com.groupir.backend.service;

import com.groupir.backend.model.Product;
import com.groupir.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceProduct {

    @Autowired
    private ProductRepository productRepository;

    /**
     * find all product from database
     *
     * @return list of all product
     */
    public List<Product> findAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    /**
     * find one product from database
     *
     * @return one product
     */
    public Product findOne(long idProduct) {
        try {
            return productRepository.findById(idProduct).get();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Add a product in database
     *
     * @param newProduct is a product
     */
    public void add(Product newProduct) {
        productRepository.save(newProduct);
    }

    /**
     * Delete a product in database
     *
     * @param idProduct
     */
    public void delete(long idProduct) {
        productRepository.deleteById(idProduct);
    }

    /**
     * update a product drom database
     *
     * @param updateProduct is a product
     */
    public void update(Product updateProduct) {
        productRepository.save(updateProduct);
    }

    /**
     * check the id of product in database
     *
     * @param idProduct is id of product
     * @return false if isn't present in database else false
     */
    public boolean findById(long idProduct) {
        return productRepository.findById(idProduct).isPresent();
    }
}
