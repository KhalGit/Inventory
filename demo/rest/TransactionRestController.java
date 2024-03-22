package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.TransactionDTO;
import com.gkalogeroudis.springexercise.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction")
    public List<TransactionDTO> findAllTransaction() {
        return transactionService.findAllTransaction();
    }

    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {

        TransactionDTO theTransactionDTO = transactionService.findByTransactionId(id);

        return theTransactionDTO;
    }

    @PutMapping("/transaction/{id}")
    public TransactionDTO updateTransaction(@RequestBody TransactionDTO theTransactionDTO, @PathVariable("id") Long transactionId) {

        return transactionService.updateTransaction(theTransactionDTO, transactionId);
    }

    @PostMapping("/transaction")
    public TransactionDTO addTransaction(@RequestBody TransactionDTO theTransactionDTO) {
        theTransactionDTO.setId(null);
        return transactionService.saveTransaction(theTransactionDTO);

    }

    @DeleteMapping("/transaction/{id}")
    public String deleteTransaction(@PathVariable("id") Long transactionId) {

        TransactionDTO tempTransactionDTO = transactionService.findByTransactionId(transactionId);

        // throw exception if null

        if (tempTransactionDTO == null) {
            throw new RuntimeException("Transaction id not found - " + transactionId);
        }

        transactionService.deleteByTransactionId(transactionId);

        return "Deleted transaction id - " + transactionId;
    }
}
