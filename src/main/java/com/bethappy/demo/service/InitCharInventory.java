package com.bethappy.demo.service;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class InitCharInventory {
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ResourcesRepository resourcesRepository;

    public void initResources(Characters characters, Long resourceId){
    Optional<Resource> existingResource = resourcesRepository.findById(resourceId);
    if(!existingResource.isPresent()){
        throw new NullPointerException("resourceId is invalid, check database for a valid resourceId");
    }else {
        Inventory newInv = new Inventory(characters, existingResource.get(), 0);
        inventoryRepository.save(newInv);
    }
    }
}
