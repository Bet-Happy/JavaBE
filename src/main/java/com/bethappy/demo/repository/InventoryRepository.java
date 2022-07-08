package com.bethappy.demo.repository;

import com.bethappy.demo.model.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {
}
