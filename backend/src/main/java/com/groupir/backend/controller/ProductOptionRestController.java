package com.groupir.backend.controller;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.ProductOption;
import com.groupir.backend.service.ServiceProduct;
import com.groupir.backend.service.ServiceProductOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/product")
public class ProductOptionRestController {

    @Autowired
    private ServiceProductOption serviceProductOption;

    @Autowired
    private ServiceProduct serviceProduct;

    /**
     * the get request is "/api/product/productOption/list" to use this method
     *
     * @return list of all productOption
     */
    @GetMapping("/productOption/list")
    public ResponseEntity<List<ProductOption>> getAllProductOption() {
        List<ProductOption> productOptions = serviceProductOption.findAllProductOption();
        if (productOptions.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productOptions, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/product/productOption/productOptionID" to use this method
     *
     * @return list of all productOption
     */
    @GetMapping("/productOption/{id}")
    public ResponseEntity getOneProductOption(@PathVariable("id") long idProductOption) {
        ProductOption productOption = serviceProductOption.findOne(idProductOption);
        if (productOption == null) {
            return new ResponseEntity<>(("ProductOption with id " + idProductOption + " not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productOption, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/product/{productID}/productOption/list" to use this method
     *
     * @return list of all productOption for a given product
     */
    @GetMapping("{id}/productOption/list")
    public ResponseEntity getAllProductOption(@PathVariable("id") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " not found",HttpStatus.NOT_FOUND);
        } else {
            List<ProductOption> productOptions = product.getProductOptions();
            if (productOptions.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(productOptions, HttpStatus.OK);
            }
        }

    }

    /**
     * the post request is "/api/product/productOption/add" to use this method
     *
     * @param newProductOption is a JSON of productOption
     * @return String
     */
    @PostMapping(value = "{id}/productOption/add", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductOption(@RequestBody ProductOption newProductOption, @PathVariable("id") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " not found", HttpStatus.NOT_FOUND);
        } else {
            newProductOption.setProduct(product);
            serviceProductOption.add(newProductOption);
            return new ResponseEntity<>("ProductOption added successfully", HttpStatus.OK);
        }

    }

    /**
     * the delete request is "/api/product/productOption/delete/{id}" to use this method
     *
     * @param idProductOption is the variable "id" in the request
     * @return String
     */
    @DeleteMapping("/productOption/delete/{id}")
    public ResponseEntity<String> deleteProductOption(@PathVariable("id") int idProductOption) {
        if (!serviceProductOption.isPresent(idProductOption)) {
            return new ResponseEntity<>("ProductOption with id " + idProductOption + " is not found", HttpStatus.NOT_FOUND);
        }
        serviceProductOption.delete(idProductOption);
        return new ResponseEntity<>("ProductOption with id " + idProductOption + " deleted", HttpStatus.OK);
    }

    /**
     * the put request is "/api/product/productOption/update" to use this method
     *
     * @param updateProductOption is a JSON of productOption
     * @param idProductOption     is id of productOption
     * @return string
     */
    @PutMapping(value = "{idProduct}/productOption/update/{idProductOption}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductOption(@RequestBody ProductOption updateProductOption, @PathVariable(name = "idProductOption") long idProductOption, @PathVariable("idProduct") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " is not found", HttpStatus.NOT_FOUND);
        } else {
            if (!serviceProductOption.isPresent(idProductOption)) {
                return new ResponseEntity<>("ProductOption with id " + idProductOption + " is not found", HttpStatus.NOT_FOUND);
            }
            updateProductOption.setOptionId(idProductOption);
            updateProductOption.setProduct(product);
            serviceProductOption.update(updateProductOption);
            return new ResponseEntity<>("ProductOption with id " + idProductOption + " updated", HttpStatus.OK);
        }
    }

}