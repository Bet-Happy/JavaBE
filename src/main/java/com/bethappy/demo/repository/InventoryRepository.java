package com.bethappy.demo.repository;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {
  List<Inventory> findAllByCharacters(Characters character);
  
  //me bullshitting after work
  //Optional<Inventory> findResourceByCharacters(Characters character, Resource resource);
}
