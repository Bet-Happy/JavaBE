package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CharactersRepository charactersRepository;

    @Autowired
    ResourcesRepository resourcesRepository;
    @CrossOrigin
    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        List<Inventory> inventoryList = (List<Inventory>) inventoryRepository.findAll();
        ArrayList<Object> newInventoryList = new ArrayList<>();
        for (Inventory inventory : inventoryList) {
            Map<String,Object> invMap = new HashMap<>();
            invMap.put("id",inventory.getId());
            invMap.put("character",inventory.getCharacters().getId());
            invMap.put("resource",inventory.getResource().getName());
            invMap.put("amount",inventory.getAmount());
            newInventoryList.add(invMap);
        }
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",newInventoryList);
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
//    @PostMapping("/inventory")
//    public ResponseEntity<Object> addCharacterItem(@RequestBody JSONObject item){
//        // insert a function here from itemcreation
//        // this could return an inventory
//        Long charId = (Long) item.get("characters");
//        Optional<Characters> character = charactersRepository.findById(charId);
//        if(!character.isPresent()){
//            return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN,false,"Character not found.",character);
//        }
//        Long resId = (Long) item.get("resource");
//        Optional<Resource> resource = resourcesRepository.findById(resId);
//        if(!resource.isPresent()){
//            return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN,false,"Resource not found.",resource);
//        }
//        ItemsHandler.createItem(character, resId);
//
//        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the inventory",c);
//    }

}
