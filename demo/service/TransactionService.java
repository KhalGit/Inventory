package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

	public List<TransactionDTO> findAllTransaction();
	
	public TransactionDTO findByTransactionId(Long theTransactionId);
	
	public TransactionDTO saveTransaction(TransactionDTO theTransactionDTO);
	
	public void deleteByTransactionId(Long theTransactionId);

	public TransactionDTO updateTransaction(TransactionDTO theTransactionDTO, Long transactionId);
	
}
