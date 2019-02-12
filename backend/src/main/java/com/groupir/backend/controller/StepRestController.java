package com.groupir.backend.controller;

import com.groupir.backend.model.Product;
import com.groupir.backend.model.Step;
import com.groupir.backend.service.ServiceProduct;
import com.groupir.backend.service.ServiceStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/product")
public class StepRestController {

    @Autowired
    private ServiceStep serviceStep;

    @Autowired
    private ServiceProduct serviceProduct;

    /**
     * the get request is "/api/product/step/list" to use this method
     *
     * @return list of all step
     */
    @GetMapping("/step/list")
    public ResponseEntity<List<Step>> getAllStep() {
        List<Step> steps = serviceStep.findAllStep();
        if (steps.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(steps, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/product/step/stepID" to use this method
     *
     * @return one step
     */
    @GetMapping("/step/{id}")
    public ResponseEntity getOneStep(@PathVariable("id") long idStep) {
        Step step = serviceStep.findOne(idStep);
        if (step == null) {
            return new ResponseEntity<>("Step with id " + idStep + " not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(step, HttpStatus.OK);
        }

    }

    /**
     * the get request is "/api/product/{productID}/step/list" to use this method
     *
     * @return list of all step for a given product
     */
    @GetMapping("{id}/step/list")
    public ResponseEntity getAllStep(@PathVariable("id") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " not found",HttpStatus.NOT_FOUND);
        } else {
            List<Step> steps = product.getSteps();
            if (steps.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(steps, HttpStatus.OK);
            }
        }

    }

    /**
     * the post request is "/api/product/step/add" to use this method
     *
     * @param newStep is a JSON of step
     * @return String
     */
    @PostMapping(value = "{id}/step/add", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStep(@RequestBody Step newStep, @PathVariable("id") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " not found", HttpStatus.NOT_FOUND);
        } else {
            newStep.setProduct(product);
            serviceStep.add(newStep);
            return new ResponseEntity<>("Step added successfully", HttpStatus.OK);
        }

    }

    /**
     * the delete request is "/api/product/step/delete/{id}" to use this method
     *
     * @param idStep is the variable "id" in the request
     * @return String
     */
    @DeleteMapping("/step/delete/{id}")
    public ResponseEntity<String> deleteStep(@PathVariable("id") int idStep) {
        if (!serviceStep.isPresent(idStep)) {
            return new ResponseEntity<>("Step with id " + idStep + " is not found", HttpStatus.NOT_FOUND);
        }
        serviceStep.delete(idStep);
        return new ResponseEntity<>("Step with id " + idStep + " deleted", HttpStatus.OK);
    }

    /**
     * the put request is "/api/product/step/update" to use this method
     *
     * @param updateStep is a JSON of step
     * @param idStep     is id of step
     * @return string
     */
    @PutMapping(value = "{idProduct}/step/update/{idStep}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateStep(@RequestBody Step updateStep, @PathVariable(name = "idStep") long idStep, @PathVariable("idProduct") long idProduct) {
        Product product = serviceProduct.findOne(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product with id " + idProduct + " is not found", HttpStatus.NOT_FOUND);
        } else {
            if (!serviceStep.isPresent(idStep)) {
                return new ResponseEntity<>("Step with id " + idStep + " is not found", HttpStatus.NOT_FOUND);
            }
            updateStep.setStepId(idStep);
            updateStep.setProduct(product);
            serviceStep.update(updateStep);
            return new ResponseEntity<>("Step with id " + idStep + " updated", HttpStatus.OK);
        }
    }

}