package com.bethappy.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.InventoryRepository;

@Service
public class ItemsHandler {
  @Autowired
  static
  InventoryRepository inventoryRepository;

  public static void createItem(Characters character, Resource resource){
    Long character_id = character.getId();
    Inventory inventory = inventoryRepository.findByCharacters_Id(character_id).orElse(null);
    Long inventory_id = inventory.getId();

    System.out.println(character_id);
    System.out.println(inventory_id);
    System.out.println(inventory);
    System.out.println(resource);
  }
}
