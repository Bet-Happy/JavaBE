package com.bethappy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

// ADDED
import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
// NOT NECESSARY FOR NOW
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;

@RestController
public class CharactersController {
  
  @Autowired
  CharactersRepository characterRepository;

  @GetMapping("/characters")
  public RedirectView getCharacters(){
    return new RedirectView("/hello");
  }

}
