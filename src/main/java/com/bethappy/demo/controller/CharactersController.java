package com.bethappy.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

// ADDED
import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.bethappy.demo.service.ResponseHandler;
//JSON HANDLING
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// NOT NECESSARY FOR NOW
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CharactersController {
  
  @Autowired
  CharactersRepository characterRepository;

  @GetMapping("/character")
  public ResponseEntity<?> characterReturn(){
    try {
      Characters character = characterRepository.findAll().get(0);
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
