package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
  @GetMapping("/character")
  public ResponseEntity<?> characterReturn(){
    try {
      Characters character = charactersRepository.findAll().get(0);
      //List<Characters> character = charactersRepository.findAll();
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The character is returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No character was found.", e);
    }
  }
  @PostMapping("/character")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character){
    charactersRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }

  @PostMapping("/character/updateExperience")
  // UPDATE THIS WHEN THE OTHER keys ARE IMPLEMENTED
  // DO THIS WITH THE ID
  public ResponseEntity<?> updateExperience(@RequestBody JSONObject skillExperience){
    Object[] keys = skillExperience.keySet().toArray();
    Object[] values = skillExperience.values().toArray();
    Long characterID = Integer.valueOf(values[0].toString()).longValue();
    Optional<Characters> character = charactersRepository.findById(characterID);
    if ((keys[1].equals("mining"))||(keys[0].equals("mining"))){
      character.orElseThrow().setMining(Integer.valueOf(values[1].toString()));
      charactersRepository.save(character.orElseThrow());
    }
    // if (keys[1].equals("smithing")) {
    //   characterID = Integer.valueOf(values[0].toString()).longValue();
    //   System.out.println(character);
    //   character.orElseThrow().setSmithing(character.orElseThrow().getSmithing() + Integer.valueOf(values[1].toString()));
    //   charactersRepository.save(character.orElseThrow()); 
    // }
    if (keys[0].equals("smithing")){
      characterID = Integer.valueOf(values[1].toString()).longValue();
      character = charactersRepository.findById(characterID);
      character.orElseThrow().setSmithing(Integer.valueOf(values[0].toString()));
      charactersRepository.save(character.orElseThrow());
    }
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Experience updated", character);
  }

  /* 
   * 1. takes items from the resources inventory by name
   *    A, receiving a request with 
   * 2. adds a new item to the inventory by name to a RANDOM(no) slot/increases the amount of an item
   * 
   */
}

