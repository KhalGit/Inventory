package com.gkalogeroudis.springexercise.demo.dto;

import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelfDTO {

    private Long id;
    private String barcode;
    private Long inventoryId;
    private List<TransactionDetailsDTO> transactionDetails = new ArrayList<>();
    private List<StockDTO> stocks = new ArrayList<>();

    public ShelfDTO(ShelfDTO theShelfDTO) {
    }

    public ShelfDTO(String barcode, Long inventoryId) {
        this.barcode = barcode;
        this.inventoryId = inventoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public List<TransactionDetailsDTO> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetailsDTO> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public List<StockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDTO> stocks) {
        this.stocks = stocks;
    }

    public ShelfDTO(Shelf shelf) {
        BeanUtils.copyProperties(shelf, this, "stocks", "transactionDetails");

        this.inventoryId = shelf.getInventory().getId();

        for (TransactionDetails s : shelf.getTransactionDetails()) {
            transactionDetails.add(new TransactionDetailsDTO(s));
        }

        for (Stock s : shelf.getStocks())   {
            stocks.add(new StockDTO(s));
        }
    }
}