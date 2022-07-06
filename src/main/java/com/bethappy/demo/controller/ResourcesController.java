package com.bethappy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.bethappy.demo.repository.ResourcesRepository;

@Controller
public class ResourcesController{

  @Autowired
  ResourcesRepository resourcesRepository;

  

}