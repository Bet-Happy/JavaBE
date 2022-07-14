package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bethappy.demo.service.ResponseHandler;
import org.springframework.http.ResponseEntity;

 @Controller
 public class CharactersController {
  
   @Autowired
   CharactersRepository characterRepository;

  @GetMapping("/character")
  public ResponseEntity<?> characterReturn(){
    try {
      List<Characters> character = characterRepository.findAll();
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The character is returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
    }
  }

  @PostMapping("/character")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character){
    characterRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }
}

