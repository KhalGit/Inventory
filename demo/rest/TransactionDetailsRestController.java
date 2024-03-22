package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;
import com.gkalogeroudis.springexercise.demo.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class TransactionDetailsRestController {

    @Autowired
    private TransactionDetailsService transactionDetailsService;

    @GetMapping("/transactiondetails")
    public List<TransactionDetailsDTO> findAllTransactionDetails() {
        return transactionDetailsService.findAllTransactionDetails();
    }

    @GetMapping("/transactiondetails/{id}")
    public TransactionDetailsDTO getTransactionDetails(@PathVariable Long id) {

        TransactionDetailsDTO theTransactionDetailsDTO = transactionDetailsService.findByTransactionDetailsId(id);

        return theTransactionDetailsDTO;
    }

    @PutMapping("/transactiondetails/{id}")
    public TransactionDetailsDTO updateTransactionDetails(@RequestBody TransactionDetailsDTO theTransactionDetailsDTO, @PathVariable("id") Long transactionDetailsId) {

        return transactionDetailsService.updateTransactionDetails(theTransactionDetailsDTO, transactionDetailsId);
    }

    @PostMapping("/transactiondetails")
    public TransactionDetailsDTO addTransactionDetails(@RequestBody TransactionDetailsDTO theTransactionDetailsDTO) {
        theTransactionDetailsDTO.setId(null);
        return transactionDetailsService.addTransactionDetails(theTransactionDetailsDTO);

    }

    @DeleteMapping("/transactiondetails/{id}")
    public String deleteTransactionDetails(@PathVariable("id") Long transactionDetailsId) {

        TransactionDetailsDTO tempTransactionDetailsDTO = transactionDetailsService.findByTransactionDetailsId(transactionDetailsId);

        // throw exception if null

        if (tempTransactionDetailsDTO == null) {
            throw new RuntimeException("Transaction id not found - " + transactionDetailsId);
        }

        transactionDetailsService.deleteByTransactionDetailsId(transactionDetailsId);

        return "Deleted transactionDetails id - " + transactionDetailsId;
    }
}
