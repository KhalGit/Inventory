package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.ProductDTO;
import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import com.gkalogeroudis.springexercise.demo.dto.TransactionDTO;
import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;
import com.gkalogeroudis.springexercise.demo.entity.*;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import com.gkalogeroudis.springexercise.demo.repository.StockRepository;
import com.gkalogeroudis.springexercise.demo.repository.TransactionRepository;
import com.gkalogeroudis.springexercise.demo.rest.StockRestController;
import com.gkalogeroudis.springexercise.demo.validation.ProductValidator;
import com.gkalogeroudis.springexercise.demo.validation.ShelfValidator;
import com.gkalogeroudis.springexercise.demo.validation.TransactionDetailsValidator;
import com.gkalogeroudis.springexercise.demo.validation.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShelfRepository shelfRepository;
	@Autowired
	private	StockRepository stockRepository;
	@Autowired
	private StockService stockService;
	@Autowired
	private StockServiceImpl stockServiceImpl;
	@Autowired
	private TransactionValidator transactionValidator;
	@Autowired
	private TransactionDetailsValidator transactionDetailsValidator;
	@Autowired
	private ProductValidator productValidator;
	@Autowired
	private ShelfValidator shelfValidator;
	@Autowired
	private StockRestController stockRestController;

	@Override
	public List<TransactionDTO> findAllTransaction() {
		List<Transaction> theTransactions = transactionRepository.findAll();
		List<TransactionDTO> theTransactionsDTO = new ArrayList<>();

		for (Transaction t: theTransactions){
			theTransactionsDTO.add(new TransactionDTO(t));
		}

		if (theTransactionsDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}

		return theTransactionsDTO;
	}

	@Override
	public TransactionDTO findByTransactionId(Long transactionId) {
		transactionValidator.idNotFound(transactionId);
		Transaction theTransaction = transactionRepository.findById(transactionId.longValue())
				.orElseThrow(ArithmeticException::new);
		return new TransactionDTO(theTransaction);
	}

	@Override
	public TransactionDTO saveTransaction(TransactionDTO theTransactionDTO) {
		transactionValidator.descriptionEmpty(theTransactionDTO.getDescription());
		transactionValidator.impExportNull(theTransactionDTO.getImportExport());
		transactionValidator.transportationEmpty(theTransactionDTO.getDeliver(), theTransactionDTO.getReceiver());

		Transaction theTransaction = new Transaction(theTransactionDTO);
		BigDecimal totalprice = BigDecimal.valueOf(0);

 		for(TransactionDetailsDTO td : theTransactionDTO.getTransactionDetailsDTO()) {
			transactionDetailsValidator.transDetailsQuantity(td.getQuantity());
			productValidator.idNotFound(td.getProductId());
			shelfValidator.idNotFound(td.getShelfId());

			Product product = productRepository.findById(td.getProductId()).orElseThrow(ArithmeticException::new);
			Shelf shelf = shelfRepository.findById(td.getShelfId()).orElseThrow(ArithmeticException::new);
			totalprice = product.getPrice().multiply(td.getQuantity());
			ProductDTO productDTO = new ProductDTO(product);
			//BeanUtils.copyProperties(product, productDTO);

			td.setTransactionId(theTransaction.getId());
			TransactionDetails transactionDetails = new TransactionDetails(td);
			transactionDetails.setTransaction(theTransaction);

			transactionDetails.setShelf(shelf);
			transactionDetails.setProduct(product);

			ImpExport impExport = theTransaction.getImportExport();
			BigDecimal quantity = td.getQuantity();
			BigDecimal nullQuantity = null;
			boolean flag = false;
			switch (impExport) {
				case OUTPUT:
					for (StockDTO theStockDTO : productDTO.getStocks()) {
						//theStockDTO = stockService.findByStockId(td.getProductId());
						if (theStockDTO.getProductId() == td.getProductId() && theStockDTO.getShelfId() == td.getShelfId()) {

							if (theStockDTO.getQuantity().compareTo(quantity) >= 0) {
								theStockDTO.setQuantity(theStockDTO.getQuantity().subtract(quantity));
								quantity = null;
								stockServiceImpl.updateStock(theStockDTO, theStockDTO.getId());
								if (theStockDTO.getQuantity() == nullQuantity) {
									stockRestController.deleteStock(theStockDTO.getId());
								}
							} else {
								quantity.subtract(theStockDTO.getQuantity());
								stockRestController.deleteStock(theStockDTO.getId());
							}

							if (quantity == null) {
								break;
							}


						}
					}
					break;
				case INPUT:
					for (StockDTO theStockDTO : productDTO.getStocks()) {
						//theStockDTO = stockService.findByStockId(td.getProductId());
						if (theStockDTO.getProductId() == td.getProductId() && theStockDTO.getShelfId() == td.getShelfId()) {
							theStockDTO.setQuantity(theStockDTO.getQuantity().add(quantity));
							stockServiceImpl.updateStock(theStockDTO, theStockDTO.getId());
							flag = true;

						}

					}
					if (flag == false){
						StockDTO theStockDTO = new StockDTO();
						theStockDTO.setQuantity(td.getQuantity());
						theStockDTO.setDate(theTransactionDTO.getDate());
						theStockDTO.setProductId(td.getProductId());
						theStockDTO.setShelfId(td.getShelfId());
						stockServiceImpl.addStock(theStockDTO);
					}
					break;
				}
				theTransaction.addTransactionDetails(transactionDetails);
		 }

		theTransaction.setTotalPrice(totalprice);

		theTransaction = transactionRepository.save(theTransaction);
		return toDTO(theTransaction);
	}

	@Override
	public void deleteByTransactionId(Long transactionId) {
		transactionValidator.idNotFound(transactionId);
		transactionRepository.deleteById(transactionId.longValue());
	}

	@Override
	public TransactionDTO updateTransaction(TransactionDTO theTransactionDTO, Long transactionId) {
		transactionValidator.idNotFound(transactionId);
		transactionValidator.descriptionEmpty(theTransactionDTO.getDescription());
		transactionValidator.impExportNull(theTransactionDTO.getImportExport());
		transactionValidator.transportationEmpty(theTransactionDTO.getDeliver(), theTransactionDTO.getReceiver());

		Transaction theTransaction = transactionRepository.findById(transactionId.longValue()).get();

		if (Objects.nonNull(theTransactionDTO.getTotalPrice())&& !"".equals(theTransactionDTO.getTotalPrice())) {
			theTransaction.setTotalPrice(theTransactionDTO.getTotalPrice());
		}

		if (Objects.nonNull(theTransactionDTO.getDescription()) && !"".equalsIgnoreCase(theTransactionDTO.getDescription())) {
			theTransaction.setDescription(theTransactionDTO.getDescription());
		}

		if (Objects.nonNull(theTransactionDTO.getDate()) && !"".equals(theTransactionDTO.getDate())) {
			theTransaction.setDate(theTransactionDTO.getDate());
		}

		if (Objects.nonNull(theTransactionDTO.getImportExport()) && !"".equals(theTransactionDTO.getImportExport())) {
			theTransaction.setImportExport(theTransactionDTO.getImportExport());
		}

		if (Objects.nonNull(theTransactionDTO.getReceiver()) && !"".equalsIgnoreCase(theTransactionDTO.getReceiver())) {
			theTransaction.setReceiver(theTransactionDTO.getReceiver());
		}

		if (Objects.nonNull(theTransactionDTO.getDeliver()) && !"".equalsIgnoreCase(theTransactionDTO.getDeliver())) {
			theTransaction.setDeliver(theTransactionDTO.getDeliver());
		}

		theTransaction = transactionRepository.save(theTransaction);

		return toDTO(theTransaction);
	}

	private TransactionDTO toDTO(Transaction transaction)	{
		TransactionDTO dto = new TransactionDTO(transaction);
		if	(transaction.getTransactionDetails() != null) {
			for (TransactionDetails td : transaction.getTransactionDetails()) {
				dto.getTransactionDetailsDTO().add(new TransactionDetailsDTO(td));
			}
		}

		return dto;
	}

}