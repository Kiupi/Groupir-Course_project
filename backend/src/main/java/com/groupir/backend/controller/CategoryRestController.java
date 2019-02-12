package com.groupir.backend.controller;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.Category;
import com.groupir.backend.service.ServiceProduct;
import com.groupir.backend.service.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/category")
public class CategoryRestController {

    @Autowired
    private ServiceCategory serviceCategory;

    /**
     * the get request is "/api/category/list" to use this method
     *
     * @return list of all category
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categories = serviceCategory.findAllCategory();
        if (categories.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/category/list/ids" to use this method
     *
     * @return list of all category's IDs
     */
    @GetMapping("/list/ids")
    public ResponseEntity<List<Integer>> getAllCategoryIds() {
        List<Integer> categories = serviceCategory.findAllCategoryIds();
        if (categories.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            return new ResponseEntity<>(categories, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/{id}" to use this method
     *
     * @return list of all category
     */
    @GetMapping("/{id}")
    public ResponseEntity getOneCategory(@PathVariable("id") int idCategory) {
        Category category = serviceCategory.findOne(idCategory);
        if (category == null) {
            return new ResponseEntity<>("Category with id " + idCategory + " not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }

    }

    /**
     * the post request is "/api/category/add" to use this method
     *
     * @param newCategory is a JSON of category
     * @return String
     */
    @PostMapping(value = "/add", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCategory(@RequestBody Category newCategory, @PathVariable("id") long idProduct) {
        serviceCategory.add(newCategory);
        return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
    }

    /**
     * the delete request is "/api/category/delete/{id}" to use this method
     *
     * @param idCategory is the variable "id" in the request
     * @return String
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int idCategory) {
        if (!serviceCategory.isPresent(idCategory)) {
            return new ResponseEntity<>("Category with id " + idCategory + " is not found", HttpStatus.NOT_FOUND);
        }
        serviceCategory.delete(idCategory);
        return new ResponseEntity<>("Category with id " + idCategory + " deleted", HttpStatus.OK);
    }

    /**
     * the put request is "/api/category/update/id" to use this method
     *
     * @param updateCategory is a JSON of category
     * @param idCategory     is id of category
     * @return string
     */
    @PutMapping(value = "/update/{idCategory}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateCategory(@RequestBody Category updateCategory, @PathVariable(name = "idCategory") int idCategory) {
        if (!serviceCategory.isPresent(idCategory)) {
            return new ResponseEntity<>("Category with id " + idCategory + " is not found", HttpStatus.NOT_FOUND);
        }
        updateCategory.setCategoryId(idCategory);
        serviceCategory.update(updateCategory);
        return new ResponseEntity<>("Category with id " + idCategory + " updated", HttpStatus.OK);

    }

}