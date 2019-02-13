package com.groupir.backend.controller;

import com.groupir.backend.model.Price;
import com.groupir.backend.model.PriceKey;
import com.groupir.backend.model.Step;
import com.groupir.backend.service.ServicePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/api/price")
public class PriceRestController {
    @Autowired
    private ServicePrice servicePrice;

    /**
     *  the post request is "/api/price/add" to use this method
     * @param price  the new price
     * @return price added
     */
    @PostMapping(value = "/add",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addPrice(@RequestBody Price price)
    {
        Price newPrice= servicePrice.add(price);
        return new ResponseEntity<>(newPrice, HttpStatus.OK);
    }

    /**
     *  the delete request is "/api/price/delete/{idOpt}/{idStep}" to use this method
     * @param idOption
     * @param idStep
     * @return string
     */
    @DeleteMapping("/delete/{idOpt}/{idStep}")
    public ResponseEntity delete(@PathVariable("idOpt") long idOption,@PathVariable("idStep") long idStep){
        servicePrice.delete(idOption,idStep);
        return new ResponseEntity<>("Price deleted",HttpStatus.OK);
    }

    /**
     *  the put request is "/api/price/update/{idOpt}/{idStep}" to use this method
     * @param price
     * @param idOption
     * @param idStep
     * @return price updated
     */
    @PutMapping(value = "update/{idOpt}/{idStep}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Price price,@PathVariable("idOpt") long idOption,@PathVariable("idStep") long idStep){
        Price priceUpdated=servicePrice.update(idOption,idStep,price);
        return new ResponseEntity<>(priceUpdated, HttpStatus.OK);
    }

}
