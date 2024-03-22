package com.gkalogeroudis.springexercise.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.entity.StockHistory;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryDTO {

    private Long id;
    private BigDecimal quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dates = new Date();
    private Long stockId;

    public StockHistoryDTO() {
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

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public StockHistoryDTO(StockHistory stockHistory) {
        BeanUtils.copyProperties(stockHistory, this);
        this.stockId = stockHistory.getStock().getId();
    }
}