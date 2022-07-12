package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
//
import com.bethappy.demo.service.ItemsHandler;
//
import com.bethappy.demo.service.ResponseHandler;

import net.minidev.json.JSONObject;

import java.util.Optional;

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
    @Autowired
    CharactersRepository charactersRepository;
    @Autowired
    ResourcesRepository resourcesRepository;

    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        Iterable <Inventory> inventoryList = inventoryRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",inventoryList);
    }

    @PostMapping("/inventory")
    public ResponseEntity<Object> addCharacterItem(@RequestBody JSONObject item){
        // insert a function here from itemcreation
        // this could return an inventory
        Long charId = (Long) item.get("characters");
        Optional<Characters> character = charactersRepository.findById(charId);
        if(!character.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN,false,"Character not found.",character);
        }
        Long resId = (Long) item.get("resource");
        Optional<Resource> resource = resourcesRepository.findById(resId);
        if(!resource.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN,false,"Resource not found.",resource);
        }

        ItemsHandler.createItem(character, resId);
        Inventory item = inventoryRepository.save(inventory);
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the inventory",c);
    }

}
