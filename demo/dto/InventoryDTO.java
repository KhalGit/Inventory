package com.gkalogeroudis.springexercise.demo.dto;

import com.gkalogeroudis.springexercise.demo.entity.Inventory;
import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private String description;
    private List<ShelfDTO> shelves = new ArrayList<>();

    public InventoryDTO() {
    }

    public Long getId() {
        return id;
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

    public List<ShelfDTO> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelfDTO> shelves) {
        this.shelves = shelves;
    }

    public InventoryDTO(Inventory inventory) {
        BeanUtils.copyProperties(inventory, this, "shelves");

        for(Shelf s: inventory.getShelves()){
            shelves.add(new ShelfDTO(s));
        }
    }

}
