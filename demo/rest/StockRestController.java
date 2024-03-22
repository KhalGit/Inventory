package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import com.gkalogeroudis.springexercise.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class StockRestController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public List<StockDTO> findAllStock() {
        return stockService.findAllStock();
    }

    @GetMapping("/stock/{id}")
    public StockDTO getStock(@PathVariable Long id) {

        StockDTO theStockDTO = stockService.findByStockId(id);

        return theStockDTO;
    }

    @PutMapping("/stock/{id}")
    public StockDTO updateStock(@RequestBody StockDTO theStockDTO, @PathVariable("id") Long stockId) {

        return stockService.updateStock(theStockDTO, stockId);
    }

    @PostMapping("/stock")
    public StockDTO addStockHistory(@RequestBody StockDTO theStockDTO) {
        theStockDTO.setId(null);
        return stockService.addStock(theStockDTO);

    }

    @DeleteMapping("/stock/{id}")
    public String deleteStock(@PathVariable("id") Long stockId) {

        StockDTO tempStockDTO = stockService.findByStockId(stockId);

        // throw exception if null

        if (tempStockDTO == null) {
            throw new RuntimeException("Stock id not found - " + stockId);
        }

        stockService.deleteByStockId(stockId);

        return "Deleted stock id - " + stockId;
    }
}
