package com.gkalogeroudis.springexercise.demo.dto;


import com.gkalogeroudis.springexercise.demo.entity.TransactionDetails;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsDTO {

    private Long id;
    private BigDecimal quantity;
    private Long productId;
    private Long shelfId;
    private Long transactionId;

    public TransactionDetailsDTO() {
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionDetailsDTO(TransactionDetails transactionDetails) {
        BeanUtils.copyProperties(transactionDetails, this);

        this.shelfId = transactionDetails.getShelf().getId();
        this.productId = transactionDetails.getProduct().getId();

        this.transactionId = transactionDetails.getTransaction().getId();
    }

}