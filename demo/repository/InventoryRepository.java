package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {



}
