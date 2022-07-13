package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import java.util.List;
import java.util.Optional;

import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.InitCharInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bethappy.demo.service.ResponseHandler;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;

 @Controller
 public class CharactersController {

   @Autowired
   CharactersRepository charactersRepository;
   @Autowired
   InitCharInventory initCharInventory;

   @CrossOrigin
   @GetMapping("/characters")
  public ResponseEntity<?> characterReturn(){
    try {
      List<Characters> character = charactersRepository.findAll();
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The characters are returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
    }
  }
  
  @PostMapping("/characters")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character){
    charactersRepository.save(character);
    for (int i=1 ; i < 5 ; i++){
        initCharInventory.initResources(character, (long) i);
    }
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }
     @CrossOrigin
  @PostMapping("/characters/updateExperience")
  public ResponseEntity<?> updateExperience(@RequestBody JSONObject skillExperience){
    Object[] keys = skillExperience.keySet().toArray();
    Object[] values = skillExperience.values().toArray();
    Long characterID = Integer.valueOf(values[0].toString()).longValue();
    Optional<Characters> character = charactersRepository.findById(characterID);
    if (keys[1].equals("mining")){
      character.orElseThrow().setMining(Integer.valueOf(values[1].toString()));
      charactersRepository.save(character.orElseThrow());
    }
    if (keys[0].equals("smithing")){
      characterID = Integer.valueOf(values[1].toString()).longValue();
      character = charactersRepository.findById(characterID);
      character.orElseThrow().setSmithing(Integer.valueOf(values[0].toString()));
      charactersRepository.save(character.orElseThrow());
    }
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Experience updated", character);
  }
}

