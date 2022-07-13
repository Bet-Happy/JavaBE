package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.ItemsHandler;
import com.bethappy.demo.service.ResponseHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class InventoryController {
    
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CharactersRepository charactersRepository;

    @Autowired
    ResourcesRepository resourcesRepository;

    @Autowired
    ItemsHandler itemsHandler;

    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        List<Inventory> inventoryList = (List<Inventory>) inventoryRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",inventoryList);
    }

    @PostMapping("/inventory")
    public ResponseEntity<Object> addCharacterItem(@RequestBody JSONObject bodyJson) {
        Long charId = Long.parseLong((String) bodyJson.get("characters"));
        Long resId = Long.parseLong((String) bodyJson.get("resource"));
        Integer amount = Integer.parseInt((String)bodyJson.get("amount"));
        Optional<Characters> currentChar = charactersRepository.findById(charId);
        if(!currentChar.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,false,"Character does not exist",currentChar);
        }
        Optional<Resource> currentRes = resourcesRepository.findById(resId);
        if(!currentRes.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,false,"Resource does not exist",currentRes);
        }
        Inventory newInventory = new Inventory(currentChar.get(),currentRes.get(),amount);
        Inventory item = inventoryRepository.save(newInventory);
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the character",item);
    }

    @PostMapping("/crafting/{resource}")
    public ResponseEntity<Object> craftItem(@RequestBody JSONObject information, @PathVariable String resource){
        Long char_id = Long.parseLong((String) information.get("characters"));
        Characters character = charactersRepository.findById(char_id).orElse(null);
        Resource resourceToGet = resourcesRepository.findByName(resource);
        try {
            itemsHandler.createItem(character, resourceToGet);
        } catch (IllegalStateException e) {
            return ResponseHandler.generateResponse(HttpStatus.OK,true,"Could not add item to the character", character);
        }
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the character", character);
    }
}
