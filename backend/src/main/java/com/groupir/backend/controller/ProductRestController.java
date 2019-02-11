package com.groupir.backend.controller;

import com.groupir.backend.dto.ProductDTO;
import com.groupir.backend.model.Product;
import com.groupir.backend.service.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/product")
public class ProductRestController {

    @Autowired
    private ServiceProduct serviceProduct;

    /**
     * the get request is "/api/product/list" to use this method
     *
     * @return list of all product
     */
    @GetMapping("/list")
    public ResponseEntity getAllProduct() {
        List<ProductDTO> products = serviceProduct.findAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * the post request is "/api/product/add" to use this method
     *
     * @param newProduct is a JSON of product
     * @return String
     */
    @PostMapping(value = "/add", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@RequestBody Product newProduct) {
        serviceProduct.add(newProduct);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    /**
     * the delete request is "/api/product/delete/{id}" to use this method
     *
     * @param idProduct is the variable "id" in the request
     * @return String
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int idProduct) {
        if (!serviceProduct.findById(idProduct)) {
            return new ResponseEntity<>("Product with id " + idProduct + " is not found", HttpStatus.NOT_FOUND);
        }
        serviceProduct.delete(idProduct);
        return new ResponseEntity<>("Product with id " + idProduct + " deleted", HttpStatus.OK);
    }

    /**
     * the put request is "/api/product/update" to use this method
     *
     * @param updateProduct is a JSON of product
     * @param idProduct     is id of product
     * @return string
     */
    @PutMapping(value = "/update/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@RequestBody Product updateProduct, @PathVariable(name = "id") long idProduct) {
        if (!serviceProduct.findById(idProduct)) {
            return new ResponseEntity<>("Product with id " + idProduct + " is not found", HttpStatus.NOT_FOUND);
        }
        updateProduct.setProductId(idProduct);
        serviceProduct.update(updateProduct);
        return new ResponseEntity<>("Product with id " + idProduct + " updated", HttpStatus.OK);
    }

    /**
     *  the get request is "/api/product/find/{id}" to use this method
     * @param idProduct is the variable "id" in the request
     * @return Product
     */
    @GetMapping(value = "/find/{id}")
    public ResponseEntity findOneProduct(@PathVariable(name = "id") long idProduct){
        if (!serviceProduct.findById(idProduct)) {
            return new ResponseEntity<>("Product with id " + idProduct + " is not found", HttpStatus.NOT_FOUND);
        }
        Product product=serviceProduct.findOne(idProduct);
        return new ResponseEntity<>(product,HttpStatus.OK);

    }

    /**
     *  the get request is "/api/product/findByCategory/{id}" to use this method
     * @param idCategory is the variable "id" in the request
     * @return list of product filter by category
     */
    @GetMapping(value = "/findByCategory/{id}")
    public ResponseEntity findProductsbyCategory(@PathVariable(name="id") int idCategory){
        List<ProductDTO> products=serviceProduct.findAllByCategory(idCategory);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    /**
     *  the get request is "/api/product/findProductsByName/{name}" to use this method
     * @param str is a substring of product's name
     * @return list of product filter by substring of name
     */
    @GetMapping(value = "/findProductsByName/{name}")
    public ResponseEntity findProductsByName(@PathVariable(name = "name") String str){
        List<ProductDTO> productDTOList=serviceProduct.findProductsByName(str);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }



}