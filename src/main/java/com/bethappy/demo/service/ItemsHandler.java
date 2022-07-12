package com.bethappy.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.InventoryRepository;

@Service
public class ItemsHandler {
  @Autowired
  private InventoryRepository inventoryRepository;

  public Object createItem(Characters character, Resource resource){
    List<Inventory> inventory = inventoryRepository.findAllByCharacters(character);

    /*boolean isPresent = false;
    Inventory existing = null;
    for (int i = 0; i < inventory.size(); i++) {
        Inventory item = inventory.get(i);
        if (item.getResource().getName().equals(resource.getName())) {
            isPresent = true;
            existing = item;
        }
    }*/

    switch (resource.getName()) {
      case "Bronze":
        spendMaterial(character, 1, 1);
        spendMaterial(character, 2, 1);
      break;

      default: 
        throw new IllegalStateException("Cannot craft this :(");
    }

    Optional<Inventory> existingInventory = inventory.stream()
      .filter(item -> item.getResource().getId().equals(resource.getId()))
      .findFirst();

    if (existingInventory.isPresent()) {
      Inventory existing = existingInventory.get();
      existing.setAmount(existing.getAmount()+1);
      inventoryRepository.save(existing);
    } else {
      Inventory item = new Inventory(character, resource, 1);
      inventoryRepository.save(item);
    }

    return "Hello";
  }

  private void spendMaterial(Characters character, long resourceId, int amountNeeded) {
    List<Inventory> inventory = inventoryRepository.findAllByCharacters(character);

    int currentCount = 0;
    for (int i = 0; i < inventory.size(); ++i) {
      if (inventory.get(i).getResource().getId().equals(resourceId)) {
        currentCount++;
      }
    }

    if (currentCount >= amountNeeded) {
      Inventory existingInventory = inventory.stream()
        .filter(item -> item.getResource().getId().equals(resourceId))
        .findFirst()
        .get();

      existingInventory.setAmount(existingInventory.getAmount()-amountNeeded);
    } else {
      throw new IllegalStateException("Not enough resource");
    }
  }
}
