package com.groupir.backend.service;

import com.google.common.collect.Lists;
import com.groupir.backend.dto.ProductDTO;
import com.groupir.backend.model.*;
import com.groupir.backend.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("/sql/groupir_address.sql")
@ComponentScan("com.groupir.backend.service")
class ServiceProductTest {

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
    private ServiceProduct serviceProduct;

    @Test
    void shouldAddProduct(){
        long nbProductBefore= productRepository.count();
        Product product = new Product();
        Category category= new Category();
        User user = new User();
        user.setUserId(2);
        category.setCategoryId(1);
        product.setCategory(category);
        product.setDescription("new product!");
        product.setEndDate(new Date());
        product.setMaxSales(100L);
        product.setName("product Test");
        product.setManufacturer(user);
        serviceProduct.add(product);
        Assert.assertTrue((nbProductBefore+1==productRepository.count()));
    }

    @Test
    void shouldUpdateProduct(){
        Product product=null;
        if(productRepository.findById(1L).isPresent()){
            product=productRepository.findById(1L).orElse(null);
        }else{
            fail("Product is not present");
        }
        String nameExpected = "product update";
        assert product != null;
        product.setName(nameExpected);
        Product productUpdate=serviceProduct.update(product);
        Assert.assertEquals(nameExpected,productUpdate.getName());
    }

    @Test
    void shouldDeleteProduct(){

        Assert.assertTrue(productRepository.findById(1L).isPresent());
        serviceProduct.delete(1L);
        Assert.assertFalse(productRepository.findById(1L).isPresent());
    }

    @Test
    void shouldGetAllProductByCategory(){
        final int ID_CATEGORY= 1 ;
        List<Product> allProducts= Lists.newArrayList(productRepository.findAll());
        if(!categoryRepository.findById(ID_CATEGORY).isPresent()){
            fail("Category with id 1 not found");
        }else{
            int nbProductOCategory1=allProducts.stream()
                    .filter(product -> product.getCategory().getCategoryId()==ID_CATEGORY)
                    .toArray()
                    .length;
            List<ProductDTO> products = serviceProduct.findAllByCategory(ID_CATEGORY);
            products.forEach(product -> {
                Assert.assertEquals(ID_CATEGORY,  product.getCategoryId());
            });
            Assert.assertEquals(nbProductOCategory1,products.size());
        }

    }

    @Test
    void shouldGetPriceOfOneProduct(){
        final long ID_PRODUCT=1;
        final long ID_OPTION=1;
        final long ID_STEP=1;
        BigDecimal price=serviceProduct.findPrice(ID_PRODUCT,ID_OPTION);
        PriceKey priceKey = new PriceKey();
        ProductOption productOption = new ProductOption();
        productOption.setOptionId(ID_OPTION);
        priceKey.setOption(productOption);
        Step  step = stepRepository.findById(ID_STEP).get();
        priceKey.setStep(step);
        Price p = priceRepository.findById(priceKey).get();
        Assert.assertEquals(p.getPrice(),price);

    }

    @Test
    void shouldGetProductsBySubStringName(){
        final String SUBSTRING_NAME="el";
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductDTO> productDTOList=serviceProduct.findProductsByName(SUBSTRING_NAME);
        int nbProductWithName=products.stream()
                .filter(product -> product.getName().contains(SUBSTRING_NAME))
                .toArray()
                .length;
        Assert.assertEquals(nbProductWithName,productDTOList.size());

    }
}