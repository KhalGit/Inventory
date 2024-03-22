package com.gkalogeroudis.springexercise.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.dto.StockHistoryDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="stockHistory")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class StockHistory implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "dates")
    private Date dates;

    public StockHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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

    public StockHistory(StockHistoryDTO stockHistoryDTO)    {
        BeanUtils.copyProperties(stockHistoryDTO, this, "stockId");
    }

}
