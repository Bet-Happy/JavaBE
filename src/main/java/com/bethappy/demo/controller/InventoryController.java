package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.ItemsHandler;
import com.bethappy.demo.service.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//        Map<String,Object> inv = new HashMap<>();
//        inventoryList.forEach((inventory) -> inv.put(inventoryList.,value));
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
    public String craftItem(@RequestBody JSONObject information, @PathVariable String resource){
    //public String craftItem(@RequestBody Characters character, @PathVariable String resource){
        Long char_id = Long.parseLong((String) information.get("characters"));
        Characters character = charactersRepository.findById(char_id).orElse(null);
        Resource resourceToGet = resourcesRepository.findByName(resource);
        try {
            itemsHandler.createItem(character, resourceToGet);
        } catch (IllegalStateException e) {
            return "Error";
        }
        //if resource doesnt exists
        // if (inventoryRepository.findAllByCharacters(character.contains()))
        // Inventory item = new Inventory(character, resourceToGet, 1);
        // inventoryRepository.save(item);
        //if resource already exists
        //Inventory item2 = inventoryRepository.findResourceByCharacters(character, resourceToGet).orElse(null);
        //ItemsHandler.createItem(character, resourceToGet);
        
        return "Response";
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
