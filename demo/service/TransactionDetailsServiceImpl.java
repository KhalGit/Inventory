package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;
import com.gkalogeroudis.springexercise.demo.entity.Product;
import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import com.gkalogeroudis.springexercise.demo.entity.Transaction;
import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import com.gkalogeroudis.springexercise.demo.repository.TransactionDetailsRepository;
import com.gkalogeroudis.springexercise.demo.repository.TransactionRepository;
import com.gkalogeroudis.springexercise.demo.validation.TransactionDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShelfRepository shelfRepository;
	@Autowired
	private TransactionDetailsValidator transactionDetailsValidator;

	@Override
	@Transactional
	public TransactionDetailsDTO addTransactionDetails(TransactionDetailsDTO transactionDetailsDTO) {
		transactionDetailsValidator.transactionNull(transactionDetailsDTO.getTransactionId());
		transactionDetailsValidator.transDetailsQuantity(transactionDetailsDTO.getQuantity());
		transactionDetailsValidator.productNull(transactionDetailsDTO.getProductId());
		transactionDetailsValidator.shelfNull(transactionDetailsDTO.getShelfId());


		TransactionDetails theTransactionDetails = new TransactionDetails(transactionDetailsDTO);

//		List<TransactionDetails> existingStocks = transactionDetailsRepository.findAllByPid(theTransactionDetails.getProduct().getId());
//		existingStocks = existingStocks != null ? existingStocks.stream().filter( s -> s.getShelfId() == stockH.getShelfId() ).collect(Collectors.toList()) : null;


//		for(int i=0;i<existingStocks.size();i++){
//			if(transactionDetailsDTO.getProductId().equals(existingStocks.get(i).getProduct().getId())){
//				throw new RuntimeException("Transaction id not found - " + transactionDetailsDTO);
//			}
//		}

		//		if (transactionDetailsDTO.getProductId() == existingStocks.get() {
//			throw new RuntimeException("Transaction id not found - " + transactionDetailsDTO);
//		}



		Transaction transaction = transactionRepository.getOne(transactionDetailsDTO.getTransactionId().longValue());
		Product product = productRepository.getOne(transactionDetailsDTO.getProductId());
		Shelf shelf = shelfRepository.getOne(transactionDetailsDTO.getShelfId());

		theTransactionDetails.setTransaction(transaction);
		theTransactionDetails.setProduct(product);
		theTransactionDetails.setShelf(shelf);
		theTransactionDetails = transactionDetailsRepository.save(theTransactionDetails);
		return toDTO(theTransactionDetails);
	}

	@Override
	@Transactional
	public List<TransactionDetailsDTO> findAllTransactionDetails() {
		List<TransactionDetails> theTransactionDetails = transactionDetailsRepository.findAll();
		List<TransactionDetailsDTO> theTransactionDetailsDTO = new ArrayList<>();

		for (TransactionDetails td : theTransactionDetails)	{
			theTransactionDetailsDTO.add(new TransactionDetailsDTO(td));
		}

		if (theTransactionDetailsDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}

		return theTransactionDetailsDTO;
	}

	@Override
	@Transactional
	public TransactionDetailsDTO findByTransactionDetailsId(Long transactionDetailsId) {
		transactionDetailsValidator.idNotFound(transactionDetailsId);
		TransactionDetails theTransactionDetails = transactionDetailsRepository.findById(transactionDetailsId).
				orElseThrow(ArithmeticException::new);
		return new TransactionDetailsDTO(theTransactionDetails);
	}

	@Override
	@Transactional
	public void deleteByTransactionDetailsId(Long transactionDetailsId) {
		transactionDetailsValidator.idNotFound(transactionDetailsId);
		transactionDetailsRepository.deleteById(transactionDetailsId);
	}

	public TransactionDetailsDTO updateTransactionDetails(TransactionDetailsDTO transactionDetailsDTO, Long transactionDetailsId) {
		transactionDetailsValidator.idNotFound(transactionDetailsId);
		transactionDetailsValidator.transactionNull(transactionDetailsDTO.getTransactionId());
		transactionDetailsValidator.transDetailsQuantity(transactionDetailsDTO.getQuantity());
		transactionDetailsValidator.productNull(transactionDetailsDTO.getProductId());
		transactionDetailsValidator.shelfNull(transactionDetailsDTO.getShelfId());

		TransactionDetails theTransactionDetails = transactionDetailsRepository.findById(transactionDetailsId).get();
		Transaction transaction = transactionRepository.getOne(transactionDetailsDTO.getTransactionId().longValue());
		Product product = productRepository.getOne(transactionDetailsDTO.getProductId());
		Shelf shelf = shelfRepository.getOne(transactionDetailsDTO.getShelfId());

		if (Objects.nonNull(transactionDetailsDTO.getQuantity()) && !"".equals(transactionDetailsDTO.getQuantity())) {
			theTransactionDetails.setQuantity(transactionDetailsDTO.getQuantity());
		}

		theTransactionDetails.setTransaction(transaction);
		theTransactionDetails.setProduct(product);
		theTransactionDetails.setShelf(shelf);
		theTransactionDetails = transactionDetailsRepository.save(theTransactionDetails);

		return toDTO(theTransactionDetails);
	}

	private TransactionDetailsDTO toDTO(TransactionDetails transactionDetails)	{
		TransactionDetailsDTO dto = new TransactionDetailsDTO(transactionDetails);

		return dto;
	}

}






