package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.ShelfDTO;
import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import com.gkalogeroudis.springexercise.demo.dto.TransactionDetailsDTO;
import com.gkalogeroudis.springexercise.demo.entity.Inventory;
import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.exception.NotFoundIdException;
import com.gkalogeroudis.springexercise.demo.repository.InventoryRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import com.gkalogeroudis.springexercise.demo.validation.ShelfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShelfServiceImpl implements ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ShelfValidator shelfValidator;


	@Override
	public List<ShelfDTO> findAllShelf() {

		List<Shelf> theShelves = shelfRepository.findAll();
		List<ShelfDTO> theShelvesDTO = new ArrayList<>();

		for (Shelf s: theShelves){
			theShelvesDTO.add(new ShelfDTO(s));
		}

		if (theShelvesDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}
		return theShelvesDTO;
	}

	@Override
	public ShelfDTO findByShelfId(Long shelfId) {

		shelfValidator.idNotFound(shelfId);
		Shelf theShelf = shelfRepository.findById(shelfId).orElseThrow(ArithmeticException::new);

		return new ShelfDTO(theShelf);
	}

	@Override
	public ShelfDTO addShelf(ShelfDTO theShelfDTO) {
		shelfValidator.barcodeEmpty(theShelfDTO.getBarcode());
		shelfValidator.inventoryNull(theShelfDTO.getInventoryId());

		Shelf theShelf = new Shelf(theShelfDTO);

		Inventory inventory = inventoryRepository.getOne(theShelfDTO.getInventoryId());

//		if( inventory == null ) throw new Exception()

		for(StockDTO s : theShelfDTO.getStocks()){
			s.setId(theShelf.getId());
			Stock stock = new Stock(s);
			stock.setShelf(theShelf);
			theShelf.getStocks().add(stock);
		}

		for(TransactionDetailsDTO td : theShelfDTO.getTransactionDetails()){
			td.setId(theShelf.getId());
			TransactionDetails transactionDetails = new TransactionDetails(td);
			transactionDetails.setShelf(theShelf);
			theShelf.getTransactionDetails().add(transactionDetails);
		}

		theShelf.setInventory(inventory);
		theShelf = shelfRepository.save(theShelf);
		return toDTO(theShelf);
	}

	@Override
	public void deleteByShelfId(Long shelfId) {
		shelfValidator.idNotFound(shelfId );
		shelfRepository.deleteById(shelfId);
	}

	@Override
	public ShelfDTO updateShelf(ShelfDTO theShelfDTO, Long shelfId) {
		shelfValidator.idNotFound(shelfId);
		shelfValidator.barcodeEmpty(theShelfDTO.getBarcode());
		shelfValidator.inventoryNull(theShelfDTO.getInventoryId());

		Shelf theShelf = shelfRepository.findById(shelfId).get();
		Inventory inventory = inventoryRepository.getOne(theShelfDTO.getInventoryId());

		if (Objects.nonNull(theShelfDTO.getBarcode())&& !"".equals(theShelfDTO.getBarcode())) {
			theShelf.setBarcode(theShelfDTO.getBarcode());
		}
		theShelf.setInventory(inventory);
		theShelf = shelfRepository.save(theShelf);

		return toDTO(theShelf);
	}

	private ShelfDTO toDTO(Shelf shelf){
		ShelfDTO dto = new ShelfDTO(shelf);
		for(Stock s: shelf.getStocks()){
			dto.getStocks().add(new StockDTO(s));
		}
		for(TransactionDetails td: shelf.getTransactionDetails()){
			dto.getTransactionDetails().add(new TransactionDetailsDTO(td));
		}

		return dto;
	}
}






