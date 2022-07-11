package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.service.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CharactersRepository charactersRepository;

    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        Iterable <Inventory> inventoryList = inventoryRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",inventoryList);
    }
    @PostMapping("/inventory")
    public ResponseEntity<Object> addCharacterItem(@RequestBody Inventory inventory) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
//        Map<String, String> map = objectMapper.readValue(bodyJson, typeRef);
        //get character based on id number
        Optional<Characters> currentChar = charactersRepository.findById(inventory.getCharacters().getId());
        if(!currentChar.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,false,"Character does not exist",currentChar);
        }
        System.out.println(inventory.getResource());
        Inventory item = inventoryRepository.save(inventory);
//        Map<String,Object> justItem = new HashMap<>();
//
//        justItem.put("id",item.getId());
//        justItem.put("character_id",currentChar.get().getId());
//        justItem.put("slot_number",item.getSlot_number());
//        justItem.put("amount",item.getAmount());
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the character",item);
    }
}
