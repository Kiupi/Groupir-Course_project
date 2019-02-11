package com.groupir.backend.service;

import com.google.common.collect.Lists;
import com.groupir.backend.dto.ProductDTO;
import com.groupir.backend.exceptions.CategoryNotFoundException;
import com.groupir.backend.model.*;
import com.groupir.backend.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Autowired
    private ProductOptionRepository productOptionRepository;

    /**
     * find all product from database
     *
     * @return list of all product
     */
    public List<ProductDTO> findAllProduct() {
        List<Product> products= (List<Product>) productRepository.findAll();
        List<ProductDTO> productDTOList= new ArrayList<>();
        if(products.isEmpty()){
            return productDTOList;
        }
        productDTOList= getDtoOFProduct(products);
        return productDTOList;
    }

    /**
     * find one product from database
     *
     * @return one product
     */
    public Product findOne(long idProduct) {
        try {
            return productRepository.findById(idProduct).get();
        } catch (NoSuchElementException e) {
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
    public List<ProductDTO> findAllByCategory(int idCategory) {
        if(!categoryRepository.findById(idCategory).isPresent()){
            throw new CategoryNotFoundException("Category with id "+ idCategory + " not found!");
        }
        List<Product> products = productRepository.findAllByCategory_CategoryId(idCategory);

        return getDtoOFProduct(products);
    }

    /**
     *  transform product into productDTO
     * @param products
     * @return ProductDTO
     */
    public List<ProductDTO> getDtoOFProduct(List<Product> products){
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<OrderItem> orderItems = (List<OrderItem>) orderItemRepository.findAll();
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.createTypeMap( Product.class, ProductDTO.class)
                .addMappings(mapping -> {
                    mapping.map(Product::getProductId, ProductDTO::setId);
                    mapping.map(Product::getDescription, ProductDTO::setDescription);
                    mapping.map(Product::getCategory, ProductDTO::setCategory);
                    mapping.map(Product::getName, ProductDTO::setNameProduct);
                    mapping.map(Product::getEndDate, ProductDTO::setDate);
                });
        products
            .forEach(product -> {
                ProductDTO productDTO = new ProductDTO();
                ProductOption productOption= productOptionRepository.findFirstByProduct(product);
                int somme=orderItems.stream()
                        .filter(orderItem -> orderItem.getKey().getOption().getProduct().getProductId().equals(product.getProductId()))
                        .mapToInt(OrderItem::getQuantity)
                        .sum();
                modelMapper.map(product,productDTO);

                productDTO.setNbOrder(somme);
                productDTO.setImg(productOption.getImage());
                productDTOList.add(productDTO);
            });

        return productDTOList;
    }
}
