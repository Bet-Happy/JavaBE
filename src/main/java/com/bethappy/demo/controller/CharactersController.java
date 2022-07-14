package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.User;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.bethappy.demo.service.ResponseHandler;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Controller
 public class CharactersController {
  
   @Autowired
   CharactersRepository characterRepository;
   @Autowired
   UserRepository userRepository;

  @GetMapping("/character")
  public ResponseEntity<?> characterReturn(){
    try {
      Characters character = characterRepository.findAll().get(0);
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "The character is returned", character);
    } catch(Exception e) {
      return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
    }
  }

  @PostMapping("/character/{id}")
  public ResponseEntity<?> characterCreation(@RequestBody Characters character, @PathVariable Long id){
      Optional<User> existingUser = userRepository.findById(id);
      character.setUser(existingUser.get());
      characterRepository.save(character);
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Character was created", character);
  }
}
