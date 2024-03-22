package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.InventoryDTO;

import java.util.List;

public interface InventoryService	{
	public List<InventoryDTO>	findAllInventory();

	public InventoryDTO findByInventoryId(Long inventoryId);

	public InventoryDTO addInventory(InventoryDTO theInventoryDTO);

	public void deleteByInventoryId(Long inventoryId);

	public InventoryDTO updateInventory(InventoryDTO theInventoryDTO, Long inventoryId);
}

