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
  @GetMapping("/characters")
  public ResponseEntity<?> characterReturn(){
    try {
      //Characters character = charactersRepository.findAll().get(0);
      List<Characters> character = charactersRepository.findAll();
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The characters are returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
    }
  }
  @PostMapping("/characters")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character){
    charactersRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }

  @PostMapping("/characters/updateExperience")
  // UPDATE THIS WHEN THE OTHER keys ARE IMPLEMENTED
  // DO THIS WITH THE ID
  public ResponseEntity<?> updateExperience(@RequestBody JSONObject skillExperience){
    Object[] keys = skillExperience.keySet().toArray();
//      Integer skill = (Integer) skillExperience.get("mining");
    Object[] values = skillExperience.values().toArray();
    Long characterID = Integer.valueOf(values[0].toString()).longValue();
    Optional<Characters> character = charactersRepository.findById(characterID);
    // If else statements for plan B
    if (keys[1].equals("mining")){
      character.orElseThrow().setMining(character.orElseThrow().getMining() + Integer.valueOf(values[1].toString()));
      charactersRepository.save(character.orElseThrow());
    }
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Experience updated", character);
  }
}

