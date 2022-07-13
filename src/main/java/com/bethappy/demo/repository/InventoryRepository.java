package com.bethappy.demo.repository;

import com.bethappy.demo.model.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {
    Optional<Inventory> findByCharactersId(Long id);
}
