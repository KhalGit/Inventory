package com.gkalogeroudis.springexercise.demo.dto;

import com.gkalogeroudis.springexercise.demo.entity.Measure;
import com.gkalogeroudis.springexercise.demo.entity.Product;
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
//@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String barcode;
    private String description;
    private BigDecimal price;
    private Measure measure;
    private List<TransactionDetailsDTO> transactionDetails = new ArrayList<>();
    private List<StockDTO> stocks = new ArrayList<>();

    public ProductDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
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

    public ProductDTO(Product product)  {
        BeanUtils.copyProperties(product, this, "stocks", "transactionDetails");

        if (product.getStocks() != null) {
            for (Stock st : product.getStocks()) {
                stocks.add(new StockDTO(st));
            }
        }

        if (product.getTransactionDetails() != null) {
            for (TransactionDetails td : product.getTransactionDetails()) {
                transactionDetails.add(new TransactionDetailsDTO(td));
            }
        }

    }
}
