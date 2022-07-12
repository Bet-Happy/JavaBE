package com.bethappy.demo.service;

import org.springframework.web.bind.annotation.ResponseBody;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ItemsHandler {
  
  @Autowired
  static
  InventoryRepository inventoryRepository;
  
  //('Copper', 5, 5);
  //('Tin', 5, 5), 
  //('Bronze', 8, 7), 
  //('BronzePickaxe', 15, 10);
  public static void createItem(Characters character,Resource resource){
    //1. takes two items as params
    //2. deducts them based on the id
    //
    character.getId();
    //Inventory.setAmount(item.getAmount());
    //inventoryRepository.setAmount()
    //item.setAmount(item.getAmount() + 1);

    //Inventory updatedInventory = new Inventory(character, resource, slot_number, amount);
    //inventoryRepository.save(updatedInventory);

    
  }
}
