package com.bethappy.demo.controller;

import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        Iterable <Inventory> inventoryList = inventoryRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",inventoryList);
    }
    @PostMapping("/inventory")
    public ResponseEntity<Object> addCharacterItem(@RequestBody Inventory inventory){
        Inventory item = inventoryRepository.save(inventory);
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the inventory",item);
    }




}
