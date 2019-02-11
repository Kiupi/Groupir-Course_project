package com.groupir.backend.service;

import com.groupir.backend.model.Category;
import com.groupir.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ServiceCategory {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * find all category from database
     *
     * @return list of all category
     */
    public List<Category> findAllCategory() {
        return (List<Category>) categoryRepository.findAll();
    }

    /**
     * find all category from database
     *
     * @return list of all category ids
     */
    public List<Integer> findAllCategoryIds() {
        return categoryRepository.getAllIds();
    }

    /**
     * find one category from database
     *
     * @return one category
     */
    public Category findOne(int idCategory) {
        try {
            return categoryRepository.findById(idCategory).get();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    /**
     * Add a category in database
     *
     * @param newCategory is a category
     * @return the saved entity
     */
    public Category add(Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    /**
     * Delete a category in database
     *
     * @param idCategory
     */
    public void delete(int idCategory) {
        categoryRepository.deleteById(idCategory);
    }

    /**
     * update a category drom database
     *
     * @param updateCategory is a category
     */
    public Category update(Category updateCategory) {
        return categoryRepository.save(updateCategory);
    }

    /**
     * check the id of category in database
     *
     * @param idCategory is id of category
     * @return false if isn't present in database else false
     */
    public boolean isPresent(int idCategory) {
        return categoryRepository.findById(idCategory).isPresent();
    }
}
