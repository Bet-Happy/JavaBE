package com.bethappy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bethappy.demo.model.*;
import com.bethappy.demo.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.ResponseHandler;

@Controller
public class ResourcesController{

  @Autowired
  ResourcesRepository resourcesRepository;

  @GetMapping("/resources")
  public ResponseEntity<Object> main(){
    Iterable<Resource> resources = resourcesRepository.findAll();
    
    return ResponseHandler.generateResponse(HttpStatus.OK, true, "Got all resources", resources);
  }

}