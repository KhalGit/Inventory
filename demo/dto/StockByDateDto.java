package com.gkalogeroudis.springexercise.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
//@AllArgsConstructor
@Getter
@Setter
public class StockByDateDto {


    private Long shelfId;

    private BigDecimal quantity;

    private String barcode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dates;

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public StockByDateDto() {
    }

    public StockByDateDto(Long shelfId, BigDecimal quantity, String barcode, Date dates) {
        this.shelfId = shelfId;
        this.quantity = quantity;
        this.barcode = barcode;
        this.dates = dates;
    }
}
