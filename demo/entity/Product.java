package com.gkalogeroudis.springexercise.demo.entity;

import com.gkalogeroudis.springexercise.demo.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="barcode")
    public String barcode;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure")
    private Measure measure;

    @OneToMany(mappedBy = "product",
                cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetails> transactionDetails;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stocks;

    public Product(ProductDTO productDTO)   {
        BeanUtils.copyProperties(productDTO, this, "transactionDetails", "stocks");
    }

    public Product() {
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

    public List<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addTransactionDetails(TransactionDetails tempTransactionDetails)    {

        if (transactionDetails == null) {
            transactionDetails = new ArrayList<>();
        }

        transactionDetails.add(tempTransactionDetails);

        tempTransactionDetails.setProduct(this);
    }

    public void addStock(Stock tempStock)    {

        if (stocks == null) {
            stocks = new ArrayList<>();
        }

        stocks.add(tempStock);

        tempStock.setProduct(this);
    }

}