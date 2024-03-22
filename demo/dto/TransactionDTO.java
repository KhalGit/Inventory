package com.gkalogeroudis.springexercise.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.entity.*;
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
public class TransactionDTO {

    private Long id;
    private BigDecimal totalPrice;
    private String description;
    private ImpExport importExport;
    private String receiver;
    private String deliver;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date = new Date();
    private List<TransactionDetailsDTO> transactionDetails = new ArrayList<>();

    public TransactionDTO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImpExport getImportExport() {
        return importExport;
    }

    public void setImportExport(ImpExport importExport) {
        this.importExport = importExport;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<TransactionDetailsDTO> getTransactionDetailsDTO() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetailsDTO> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public TransactionDTO(Transaction transaction)  {
        BeanUtils.copyProperties(transaction, this, "transactionDetails");

        if (transaction.getTransactionDetails() != null) {
            for (TransactionDetails td : transaction.getTransactionDetails()) {
                this.transactionDetails.add(new TransactionDetailsDTO(td));
            }
        }
    }
}