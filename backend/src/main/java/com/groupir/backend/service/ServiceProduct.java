package com.groupir.backend.service;

import com.google.common.collect.Lists;
import com.groupir.backend.exceptions.CategoryNotFoundException;
import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.Price;
import com.groupir.backend.model.Product;
import com.groupir.backend.model.Step;
import com.groupir.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceProduct {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
    public Product update(Product updateProduct) {
        return productRepository.save(updateProduct);
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


    /**
     *  find the product's price
     * @param idProduct product's id
     * @param optionId option's id
     * @return price
     */
    public BigDecimal findPrice(long idProduct,long optionId){
        List<OrderItem> orderItems= Lists.newArrayList(orderItemRepository.findAll());
        int somme=orderItems.stream()
                .filter(orderItem -> orderItem.getKey().getOption().getProduct().getProductId() == idProduct)
                .mapToInt(OrderItem::getQuantity)
                .sum();
        Step step =stepRepository.findByProduct_ProductIdAndThresholdLessThanEqualOrderByThresholdDesc(idProduct,somme);
        Price price = priceRepository.findByKey_Option_OptionIdAndKey_Step_StepId(optionId,step.getStepId());
        return price.getPrice();
    }

    /**
     * find list of product by category
     * @param idCategory category's id
     * @return list of product
     */
    public List<Product> findAllByCategory(int idCategory) {
        if(!categoryRepository.findById(idCategory).isPresent()){
            throw new CategoryNotFoundException("Category with id "+ idCategory + " not found!");
        }
        return productRepository.findAllByCategory_CategoryId(idCategory);
    }
}
