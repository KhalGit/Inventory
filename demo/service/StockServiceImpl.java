package com.gkalogeroudis.springexercise.demo.service;

import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import com.gkalogeroudis.springexercise.demo.dto.StockHistoryDTO;
import com.gkalogeroudis.springexercise.demo.entity.Product;
import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.StockHistory;
import com.gkalogeroudis.springexercise.demo.exception.DataNotFound;
import com.gkalogeroudis.springexercise.demo.repository.ProductRepository;
import com.gkalogeroudis.springexercise.demo.repository.ShelfRepository;
import com.gkalogeroudis.springexercise.demo.repository.StockRepository;
import com.gkalogeroudis.springexercise.demo.validation.StockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private ShelfRepository shelfRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockValidator stockValidator;

	@Override
	public List<StockDTO> findAllStock() {

		List<Stock> theStocks = stockRepository.findAll();
		List<StockDTO> theStocksDTO = new ArrayList<>();

		for (Stock s : theStocks)	{
			theStocksDTO.add(new StockDTO(s));
		}

		if (theStocksDTO.isEmpty())	{
			DataNotFound dataNotFound;
		}

		return theStocksDTO;
	}

	@Override
	public StockDTO findByStockId(Long stockId) {
		stockValidator.idNotFound(stockId);

		Stock theStock = stockRepository.findById(stockId).orElseThrow(ArithmeticException::new);

		return new StockDTO(theStock);
	}

	@Override
	public StockDTO addStock(StockDTO theStockDTO) {
		stockValidator.productNull(theStockDTO.getProductId());
		stockValidator.stockQuantityNegative(theStockDTO.getQuantity());
		stockValidator.shelfNull(theStockDTO.getShelfId());
		Stock theStock = new Stock(theStockDTO);

		Shelf shelf = shelfRepository.findById(theStockDTO.getShelfId()).orElseThrow(ArithmeticException::new);
		Product product = productRepository.findById(theStockDTO.getProductId()).orElseThrow(ArithmeticException::new);

//		StockHistoryDTO sh = new StockHistoryDTO();
//		sh.setStockId(theStock.getId());
//		sh.setDates(theStock.getUpToDate());
//		sh.setQuantity(theStock.getQuantity());
//		StockHistory stockHistory = new StockHistory(sh);
//		stockHistory.setStock(theStock);
//		theStock.addStockHistory(stockHistory);

		theStock.setShelf(shelf);
		theStock.setProduct(product);
		theStock = stockRepository.save(theStock);
		return toDTO(theStock);
	}

	@Override
	public void deleteByStockId(Long stockId) {
		stockValidator.idNotFound(stockId);
		stockRepository.deleteById(stockId);
	}

	@Override
	public StockDTO updateStock(StockDTO theStockDTO, Long stockId) {
		stockValidator.idNotFound(stockId);
		stockValidator.productNull(theStockDTO.getProductId());
		stockValidator.stockQuantityNegative(theStockDTO.getQuantity());
		stockValidator.shelfNull(theStockDTO.getShelfId());
		Stock theStock = stockRepository.findById(stockId).orElseThrow(ArithmeticException::new);
		Shelf shelf = shelfRepository.getOne(theStockDTO.getShelfId());
		Product product = productRepository.getOne(theStockDTO.getProductId());

		StockHistoryDTO sh = new StockHistoryDTO();
		sh.setStockId(theStock.getId());
		sh.setDates(theStock.getUpToDate());
		sh.setQuantity(theStock.getQuantity());
		StockHistory stockHistory = new StockHistory(sh);
		stockHistory.setStock(theStock);
		theStock.addStockHistory(stockHistory);

		if (Objects.nonNull(theStockDTO.getDate()) && !"".equals(theStockDTO.getDate())) {
			theStock.setUpToDate(theStockDTO.getDate());
		}

		if (Objects.nonNull(theStockDTO.getQuantity()) && !"".equals(theStockDTO.getQuantity())) {
			theStock.setQuantity(theStockDTO.getQuantity());
		}

		theStock.setShelf(shelf);
		theStock.setProduct(product);
		theStock = stockRepository.save(theStock);

		return toDTO(theStock);
	}

	private StockDTO toDTO(Stock stock)	{
		StockDTO dto = new StockDTO(stock);
		if (stock.getStockHistories() != null) {
			for (StockHistory sh : stock.getStockHistories()) {
				dto.getStockHistories().add(new StockHistoryDTO(sh));
			}
		}



		return dto;
	}
}






