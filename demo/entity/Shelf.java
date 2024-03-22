package com.gkalogeroudis.springexercise.demo.entity;

import com.gkalogeroudis.springexercise.demo.dto.ShelfDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shelf")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Shelf implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="barcode")
	private String barcode;

	@OneToMany(mappedBy = "shelf",
			cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TransactionDetails> transactionDetails = new ArrayList<>();

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;


	@OneToMany(mappedBy = "shelf",
			cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stock> stocks = new ArrayList<>();

	public Shelf (ShelfDTO shelfDTO){
		BeanUtils.copyProperties(shelfDTO, this, "stocks", "transactionDetails");
		this.inventory = new Inventory(shelfDTO.getInventoryId());
	}

	public Shelf() {
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

	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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

		tempTransactionDetails.setShelf(this);
	}

	public void addStock(Stock tempStock)    {

		if (stocks == null) {
			stocks = new ArrayList<>();
		}

		stocks.add(tempStock);

		tempStock.setShelf(this);
	}
}