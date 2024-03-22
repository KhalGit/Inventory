package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.InventoryDTO;
import com.gkalogeroudis.springexercise.demo.dto.ShelfDTO;
import com.gkalogeroudis.springexercise.demo.entity.Inventory;
import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.InventoryRepository;
import com.gkalogeroudis.springexercise.demo.validation.InventoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private InventoryValidator inventoryValidator;


	@Override
	public List<InventoryDTO> findAllInventory() {
		List<Inventory> theInventories = inventoryRepository.findAll();
		List<InventoryDTO> theInventoriesDTO = new ArrayList<>();

		for (Inventory i: theInventories){
			theInventoriesDTO.add(new InventoryDTO(i));
		}
		if (theInventoriesDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}
		return theInventoriesDTO;
	}

	@Override
	public InventoryDTO findByInventoryId(Long inventoryId) {
		inventoryValidator.idNotFound(inventoryId);
		Inventory theInventory = inventoryRepository.findById(inventoryId).orElseThrow(ArithmeticException::new); //TODO: otan kano ta validations na to diorthoso
		return new InventoryDTO(theInventory);
	}

	@Override
	public InventoryDTO addInventory(InventoryDTO theInventoryDTO) {
		inventoryValidator.descriptionEmpty(theInventoryDTO.getDescription());
		Inventory theInventory = new Inventory(theInventoryDTO);

		for(ShelfDTO s : theInventoryDTO.getShelves()){
			s.setId(theInventoryDTO.getId());
			Shelf shelf = new Shelf(s);
			shelf.setInventory(theInventory);
			theInventory.getShelves().add(shelf);
		}

		theInventory = inventoryRepository.save(theInventory);
		return toDTO(theInventory);
	}

	@Override
	public void deleteByInventoryId(Long inventoryId) {
		inventoryValidator.idNotFound(inventoryId);
		inventoryRepository.deleteById(inventoryId);
	}

	@Override
	public InventoryDTO updateInventory(InventoryDTO theInventoryDTO, Long inventoryId) {
		inventoryValidator.idNotFound(inventoryId);
		inventoryValidator.descriptionEmpty(theInventoryDTO.getDescription());
		Inventory theInventory = inventoryRepository.findById(inventoryId).get();

		if (Objects.nonNull(theInventoryDTO.getDescription()) && !"".equalsIgnoreCase(theInventoryDTO.getDescription())) {
			theInventory.setDescription(theInventoryDTO.getDescription());
		}
		theInventory = inventoryRepository.save(theInventory);
		return toDTO(theInventory);
	}

	private InventoryDTO toDTO(Inventory inventory){
		InventoryDTO dto = new InventoryDTO(inventory);
		for(Shelf s: inventory.getShelves()){
			dto.getShelves().add(new ShelfDTO(s));
		}

		return dto;
	}
}