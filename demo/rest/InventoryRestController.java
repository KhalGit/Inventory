package com.gkalogeroudis.springexercise.demo.rest;

import com.gkalogeroudis.springexercise.demo.dto.InventoryDTO;
import com.gkalogeroudis.springexercise.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
public class InventoryRestController {

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/inventory")
    public List<InventoryDTO> findAllInventory()   {
        return inventoryService.findAllInventory();
    }

    @GetMapping("/inventory/{id}")
    public InventoryDTO getInventory(@PathVariable Long id)    {
        InventoryDTO theInventoryDTO = inventoryService.findByInventoryId(id);

        return theInventoryDTO;
    }

    @PutMapping("/inventory/{id}")
    public InventoryDTO updateInventory(@RequestBody InventoryDTO theInventoryDTO, @PathVariable("id") Long inventoryId) {

        return inventoryService.updateInventory(theInventoryDTO, inventoryId);
    }

    @PostMapping("/inventory")
    public InventoryDTO addInventory(@Validated @RequestBody InventoryDTO theInventoryDTO) {
        theInventoryDTO.setId(null);
        return inventoryService.addInventory(theInventoryDTO);

    }

    @DeleteMapping("/inventory/{id}")
    public String deleteInventory(@PathVariable("id") Long inventoryId) {

       InventoryDTO tempInventoryDTO = inventoryService.findByInventoryId(inventoryId);

        // throw exception if null
        if (tempInventoryDTO == null) {
            throw new RuntimeException("Inventory id not found - " + inventoryId);
        }
        inventoryService.deleteByInventoryId(inventoryId);

        return "Deleted inventory id - " + inventoryId;
    }
}
