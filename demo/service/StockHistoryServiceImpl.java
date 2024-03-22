package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.StockByDateDto;
import com.gkalogeroudis.springexercise.demo.dto.StockHistoryDTO;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.StockHistory;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.StockHistoryRepository;
import com.gkalogeroudis.springexercise.demo.repository.StockRepository;
import com.gkalogeroudis.springexercise.demo.repository.custom.StockHistoryCustomRepository;
import com.gkalogeroudis.springexercise.demo.validation.StockHistoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service

public class StockHistoryServiceImpl implements StockHistoryService {

	@Autowired
	private StockHistoryRepository stockHistoryRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockHistoryValidator stockHistoryValidator;

	public void JPAQueryFactory(EntityManager entityManager)	{

	}

	@Override
	public List<StockHistoryDTO> findAllStockHistory() {
		List<StockHistory> theStockHistories = stockHistoryRepository.findAll();
		List<StockHistoryDTO> theStockHistoriesDTO = new ArrayList<>();

		for (StockHistory sh : theStockHistories)	{
			theStockHistoriesDTO.add(new StockHistoryDTO(sh));
		}

		if (theStockHistoriesDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}

		return theStockHistoriesDTO;
	}

	@Override
	@Transactional
	public List<StockByDateDto> recoverStockHistory(Date dates, String barcode)	{
		List<StockByDateDto> recoveredStockHistory = stockHistoryRepository.findStockHistory(dates, barcode);
//		List<StockHistoryDTO> recoveredStockHistoryDTO = new ArrayList<>();
//
//		for (StockHistory sh : recoveredStockHistory)	{
//			recoveredStockHistoryDTO.add(new StockHistoryDTO(sh));
//		}

		return recoveredStockHistory;
	}

	@Override
	public StockHistoryDTO findByStockHistoryId(Long stockHistoryId) {
		stockHistoryValidator.idNotFound(stockHistoryId);
		StockHistory theStockHistory = stockHistoryRepository.
				findById(stockHistoryId).orElseThrow(ArithmeticException::new);
		return new StockHistoryDTO(theStockHistory);
	}

	@Override
	public StockHistoryDTO addStockHistory(StockHistoryDTO theStockHistoryDTO) {
		stockHistoryValidator.stockNull(theStockHistoryDTO.getStockId());
		stockHistoryValidator.stockHistoryQuantityNegative(theStockHistoryDTO.getQuantity());

		StockHistory theStockHistory = new StockHistory(theStockHistoryDTO);
		Stock stock = stockRepository.getOne(theStockHistoryDTO.getStockId());

		theStockHistory.setStock(stock);
		theStockHistory = stockHistoryRepository.save(theStockHistory);

		return toDTO(theStockHistory);
	}

	@Override
	public void deleteByStockHistoryId(Long stockHistoryId) {
		stockHistoryValidator.idNotFound(stockHistoryId);
		stockHistoryRepository.deleteById(stockHistoryId);
	}

	@Override
	public StockHistoryDTO updateStockHistory(StockHistoryDTO theStockHistoryDTO, Long stockHistoryId) {
		stockHistoryValidator.idNotFound(stockHistoryId);
		stockHistoryValidator.stockNull(theStockHistoryDTO.getStockId());
		stockHistoryValidator.stockHistoryQuantityNegative(theStockHistoryDTO.getQuantity());

		StockHistory theStockHistory = stockHistoryRepository.findById(stockHistoryId).get();
		Stock stock = stockRepository.getOne(theStockHistoryDTO.getStockId());


		if (Objects.nonNull(theStockHistoryDTO.getQuantity()) && !"".equals(theStockHistoryDTO.getQuantity())) {
			theStockHistory.setQuantity(theStockHistoryDTO.getQuantity());
		}

		if (Objects.nonNull(theStockHistoryDTO.getDates()) && !"".equals(theStockHistoryDTO.getDates())) {
			theStockHistory.setDates(theStockHistoryDTO.getDates());
		}

		theStockHistory.setStock(stock);
		theStockHistory = stockHistoryRepository.save(theStockHistory);

		return toDTO(theStockHistory);
	}

	private StockHistoryDTO toDTO(StockHistory stockHistory)	{
		StockHistoryDTO dto = new StockHistoryDTO(stockHistory);

		return dto;
	}
}