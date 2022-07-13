package com.bethappy.demo.repository;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {
  List<Inventory> findAllByCharacters(Characters character);
}
