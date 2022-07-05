package com.bethappy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

// ADDED
import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
// NOT NECESSARY FOR NOW
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CharacterController {
  
  @Autowired
  CharacterRepository characterRepository;

  @GetMapping("/")
  public String main(Model model){
    return "/";
  }

  // @PostMapping("/")
  // public RedirectView CREATIONPLACEHOLDER(@ModelAttribute Characters character, Model model) {

  // }
}
