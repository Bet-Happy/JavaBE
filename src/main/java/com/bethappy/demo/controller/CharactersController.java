package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bethappy.demo.service.ResponseHandler;
import org.springframework.http.ResponseEntity;

 @Controller
 public class CharactersController {
   @Autowired
   CharactersRepository charactersRepository;
  @GetMapping("/character")
  public ResponseEntity<?> characterReturn(){
    try {
      Characters character = charactersRepository.findAll().get(0);
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The character is returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
    }
  }
  @PostMapping("/character")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character){
    charactersRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }

  @PostMapping("/character/updateExperience")
  // UPDATE THIS WHEN THE OTHER SKILLS ARE IMPLEMENTED
  // DO THIS WITH THE ID
  public ResponseEntity<?> updateExperience(@RequestBody Characters character){
    System.out.println("FIRST CHARACTER \n\n"+ character);
    System.out.println(character);
    character = charactersRepository.findAll().get(0);
    System.out.println("SECOND CHARACTER \n\n"+ character);
    //character.setMining(character.getMining() + experience);
    charactersRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Experience updated", character);
  }
}

