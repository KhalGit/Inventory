package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.StockDTO;

import java.util.List;

public interface StockService {

	public List<StockDTO> findAllStock();
	
	public StockDTO findByStockId(Long theStockId);
	
	public StockDTO addStock(StockDTO theStockDTO);
	
	public void deleteByStockId(Long theStockId);

	public StockDTO updateStock(StockDTO theStockDTO, Long stockId);
}
