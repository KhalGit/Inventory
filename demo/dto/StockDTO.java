package com.gkalogeroudis.springexercise.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.entity.Stock;
import com.gkalogeroudis.springexercise.demo.entity.StockHistory;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    private Long id;
    private BigDecimal quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date = new Date();
    private Long productId;
    private Long shelfId;
    private List<StockHistoryDTO> stockHistories = new ArrayList<>();

    public StockDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public List<StockHistoryDTO> getStockHistories() {
        return stockHistories;
    }

    public void setStockHistory(List<StockHistoryDTO> stockHistories) {
        this.stockHistories = stockHistories;
    }

    public StockDTO(Stock stock)    {
        BeanUtils.copyProperties(stock, this);

        this.productId = stock.getProduct().getId();
        this.shelfId = stock.getShelf().getId();

        if (stock.getStockHistories() != null) {
            for (StockHistory sh : stock.getStockHistories()) {
                stockHistories.add(new StockHistoryDTO(sh));
            }
        }
    }
}
