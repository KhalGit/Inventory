package com.gkalogeroudis.springexercise.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gkalogeroudis.springexercise.demo.dto.TransactionDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="transaction")

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Transaction implements Serializable {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @Column(name = "totalPrice")
        private BigDecimal totalPrice;

        @Column(name = "description")
        private String description;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Column(name="date")
        private Date date = new Date();

        @Enumerated(EnumType.STRING)
        @Column(name = "import_export")
        private ImpExport importExport;

        @Column(name = "receiver")
        private String receiver;

        @Column(name = "deliver")
        private String deliver;

        @OneToMany(mappedBy = "transaction",
                cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<TransactionDetails> transactionDetails;

        public Transaction(TransactionDTO transactionDTO)       {
                BeanUtils.copyProperties(transactionDTO, this, "transactionDetails");
        }

        public Transaction() {
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

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
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

        public List<TransactionDetails> getTransactionDetails() {
                return transactionDetails;
        }

        public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
                this.transactionDetails = transactionDetails;
        }

        public void addTransactionDetails(TransactionDetails tempTransactionDetails)    {

                if (transactionDetails == null) {
                        transactionDetails = new ArrayList<>();
                }

                transactionDetails.add(tempTransactionDetails);

                tempTransactionDetails.setTransaction(this);
        }
}
