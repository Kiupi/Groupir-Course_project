package com.groupir.backend.service;

import com.groupir.backend.model.Step;
import com.groupir.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
     * find one step from database
     *
     * @return one step
     */
    public Step findOne(long idStep) {
        try {
            return stepRepository.findById(idStep).get();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    /**
     * Add a step in database
     *
     * @param newStep is a step
     * @return the saved entity
     */
    public Step add(Step newStep) {
        return stepRepository.save(newStep);
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
    public Step update(Step updateStep) {
        return stepRepository.save(updateStep);
    }

    /**
     * check the id of step in database
     *
     * @param idStep is id of step
     * @return false if isn't present in database else false
     */
    public boolean isPresent(long idStep) {
        return stepRepository.findById(idStep).isPresent();
    }
}
