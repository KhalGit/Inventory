package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.ShelfDTO;

import java.util.List;

public interface ShelfService {

	public List<ShelfDTO> findAllShelf();
	
	public ShelfDTO findByShelfId(Long theShelfId);
	
	public ShelfDTO addShelf(ShelfDTO theShelfDTO);
	
	public void deleteByShelfId(Long theShelfId);

	public ShelfDTO updateShelf(ShelfDTO shelfDTO, Long shelfId);
}
