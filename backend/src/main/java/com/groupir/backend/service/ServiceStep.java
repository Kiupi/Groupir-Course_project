package com.groupir.backend.service;

import com.groupir.backend.model.Step;
import com.groupir.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceStep {

    @Autowired
    private StepRepository stepRepository;

    /**
     * find all step from database
     *
     * @return list of all step
     */
    public List<Step> findAllStep() {
        return (List<Step>) stepRepository.findAll();
    }

    /**
     * find all step from database with corresponding product id
     *
     * @return list of all steps
     */
   /* public List<Step> findAllStepByProductId(long idProduct) {
        return (List<Step>) stepRepository.findAll().;
    }*/

    /**
     * Add a step in database
     *
     * @param newStep is a step
     */
    public void add(Step newStep) {
        stepRepository.save(newStep);
    }

    /**
     * Delete a step in database
     *
     * @param idStep
     */
    public void delete(long idStep) {
        stepRepository.deleteById(idStep);
    }

    /**
     * update a step drom database
     *
     * @param updateStep is a step
     */
    public void update(Step updateStep) {
        stepRepository.save(updateStep);
    }

    /**
     * check the id of step in database
     *
     * @param idStep is id of step
     * @return false if isn't present in database else false
     */
    public boolean findById(long idStep) {
        return stepRepository.findById(idStep).isPresent();
    }
}
