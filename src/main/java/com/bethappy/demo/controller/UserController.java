package com.bethappy.demo.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.bethappy.demo.model.User;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.UserRepository;
import com.bethappy.demo.service.ResponseHandler;

import net.minidev.json.JSONObject;


@Controller
public class UserController {
  
  @Autowired
  CharactersRepository charactersRepository;
  @Autowired
  BCryptPasswordEncoder getPasswordEncoder;
  @Autowired
  UserRepository userRepository;

  
  @PostMapping("/users/registration")
  public ResponseEntity<Object> signup(@RequestBody JSONObject user){
    String username = user.get("username").toString();
    String password = user.get("password").toString();

    userRepository.save(new User(username, password));
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Created a user", user);
  }

  @GetMapping("/users/{id}")
    public ResponseEntity<Object> signup(@PathVariable Long id){
      // User user = userRepository.findAll().get(1);
      User user = userRepository.findById(Long.valueOf(1)).orElse(null);
      return ResponseHandler.generateResponse(HttpStatus.OK, true, "Create a user", user);
    }

}
  
  