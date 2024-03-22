package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;

import java.util.List;

public interface TransactionDetailsService {

	public List<TransactionDetailsDTO> findAllTransactionDetails();
	
	public TransactionDetailsDTO findByTransactionDetailsId(Long theTransactionDetailsId);
	
	public TransactionDetailsDTO addTransactionDetails(TransactionDetailsDTO theTransactionDetailsDTO);
	
	public void deleteByTransactionDetailsId(Long theTransactionDetailsId);

	public TransactionDetailsDTO updateTransactionDetails(TransactionDetailsDTO theTransactionDetailsDTO,
												Long transactionDetailsId);
}
