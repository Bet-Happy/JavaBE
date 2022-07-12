package com.bethappy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.bethappy.demo.model.User;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.UserRepository;

public class UserController {
  
  @Autowired
  CharactersRepository charactersRepository;
  @Autowired
  BCryptPasswordEncoder getPasswordEncoder;
  @Autowired
  UserRepository userRepository;

  @GetMapping("/users/new")
  public String signup(Model model){
    model.addAttribute("user",new User());
    return "users/new";
  }
  // public ResponseEntity<?> characterReturn(){
  //   try {
  //     Characters character = characterRepository.findAll().get(0);
  //     return ResponseHandler.generateResponse(HttpStatus.OK, true, "The character is returned", character);
  //   } catch(Exception e) {
  //     return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, "No characters was found.", e);
  //   }
  // }
  @PostMapping("/users")
  public RedirectView signup(@ModelAttribute User user, RedirectAttributes redirAttr){
    if (userRepository.existsByUsername(user.getUsername())){
      redirAttr.addFlashAttribute("message", "Username already exists!");
    }
    return new RedirectView("users/new");
  }
}
