package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.StockByDateDto;
import com.gkalogeroudis.springexercise.demo.dto.StockHistoryDTO;
import com.gkalogeroudis.springexercise.demo.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class StockHistoryRestController {

        @Autowired
        private StockHistoryService stockHistoryService;


        @GetMapping("/stockhistory")
        public List<StockHistoryDTO> findAllStockHistory()   {
            return stockHistoryService.findAllStockHistory();
        }

        @GetMapping("/recoverstockhistory")
        public List<StockByDateDto> recoverStockHistory(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dates, @RequestParam String barcode) {
            List<StockByDateDto> s = stockHistoryService.recoverStockHistory(dates, barcode);
            return stockHistoryService.recoverStockHistory(dates, barcode);
        }

        @GetMapping("/stockhistory/{date}")
        public StockHistoryDTO getStockHistory(@PathVariable Long id)    {

            StockHistoryDTO theStockHistoryDTO = stockHistoryService.findByStockHistoryId(id);

            return theStockHistoryDTO;
        }

        @PutMapping("/stockhistory/{id}")
        public StockHistoryDTO updateStockHistory(@RequestBody StockHistoryDTO theStockHistoryDTO, @PathVariable("id") Long stockHistoryId) {

            return stockHistoryService.updateStockHistory(theStockHistoryDTO, stockHistoryId);
        }

        @PostMapping("/stockhistory")
        public StockHistoryDTO addStockHistory(@RequestBody StockHistoryDTO theStockHistoryDTO) {
            theStockHistoryDTO.setId(null);
            return stockHistoryService.addStockHistory(theStockHistoryDTO);

        }

        @DeleteMapping("/stockhistory/{id}")
        public String deleteStockHistory(@PathVariable("id") Long stockHistoryId) {

            StockHistoryDTO tempStockHistoryDTO = stockHistoryService.findByStockHistoryId(stockHistoryId);

            // throw exception if null

            if (tempStockHistoryDTO == null) {
                throw new RuntimeException("StockHistory id not found - " + stockHistoryId);
            }

            stockHistoryService.deleteByStockHistoryId(stockHistoryId);

            return "Deleted stockHistory id - " + stockHistoryId;
        }
}
