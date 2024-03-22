package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.StockByDateDto;
import com.gkalogeroudis.springexercise.demo.dto.StockHistoryDTO;

import java.util.Date;
import java.util.List;

public interface StockHistoryService {

	public List<StockHistoryDTO> findAllStockHistory();

	public StockHistoryDTO findByStockHistoryId(Long theStockHistoryId);
	
	public StockHistoryDTO addStockHistory(StockHistoryDTO theStockHistoryDTO);
	
	public void deleteByStockHistoryId(Long theStockHistoryId);

	public StockHistoryDTO updateStockHistory(StockHistoryDTO theStockHistoryDTO, Long stockHistoryId);

	public List<StockByDateDto> recoverStockHistory(Date dates, String barcode);

}
