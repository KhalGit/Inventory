package com.gkalogeroudis.springexercise.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.dto.StockDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="stock")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="up_to_date")
    private Date upToDate = new Date();

    @Column(name="quantity")
    private BigDecimal quantity;

    @OneToMany(mappedBy = "stock",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    private List<StockHistory> stockHistories;

    public Stock(StockDTO stockDTO) {
        BeanUtils.copyProperties(stockDTO, this, "stockHistories");
    }

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getUpToDate() {
        return upToDate;
    }

    public void setUpToDate(Date upToDate) {
        this.upToDate = upToDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public List<StockHistory> getStockHistories() {
        return stockHistories;
    }

    public void setStockHistories(List<StockHistory> stockHistories) {
        this.stockHistories = stockHistories;
    }

    public void addStockHistory(StockHistory tempStockHistory)    {

        if (stockHistories == null) {
            stockHistories = new ArrayList<>();
        }

        stockHistories.add(tempStockHistory);

        tempStockHistory.setStock(this);
    }

}
