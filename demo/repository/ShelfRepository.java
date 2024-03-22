package com.gkalogeroudis.springexercise.demo.repository;

import com.gkalogeroudis.springexercise.demo.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
}
