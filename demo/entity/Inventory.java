package com.gkalogeroudis.springexercise.demo.entity;


import com.gkalogeroudis.springexercise.demo.dto.InventoryDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="inventory")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Inventory implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(name="description")
  private String description;

  @OneToMany(mappedBy = "inventory",
          cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Shelf> shelves = new ArrayList<>();

  public Inventory() {
  }

  public Long getId() {
    return id;
  }

  public Inventory(Long id) {
    this.id = id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Shelf> getShelves() {
    return shelves;
  }

  public void setShelves(List<Shelf> shelves) {
    this.shelves = shelves;
  }

  public Inventory(InventoryDTO inventoryDto){
    BeanUtils.copyProperties(inventoryDto, this, "shelves");
  }

  public void addShelf(Shelf tempShelf) {

    if (shelves == null)  {
      shelves = new ArrayList<>();
    }

    shelves.add(tempShelf);

    tempShelf.setInventory(this);
  }
}