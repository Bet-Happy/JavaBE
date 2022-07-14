package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import com.bethappy.demo.service.ItemsHandler;
import com.bethappy.demo.service.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class InventoryController {
    
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CharactersRepository charactersRepository;

    @Autowired
    ResourcesRepository resourcesRepository;

    @Autowired
    ItemsHandler itemsHandler;

    @CrossOrigin
    @GetMapping("/inventory")
    public ResponseEntity<Object> getCharacterInventory(){
        List<Inventory> inventoryList = (List<Inventory>) inventoryRepository.findAll();
        ArrayList<Object> newInventoryList = new ArrayList<>();
        for (Inventory inventory : inventoryList) {
            Map<String,Object> invMap = new HashMap<>();
            invMap.put("id",inventory.getId());
            invMap.put("character",inventory.getCharacters().getId());
            invMap.put("resource",inventory.getResource().getId());
            invMap.put("amount",inventory.getAmount());
            newInventoryList.add(invMap);
        }
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"InventoryList retrieved",newInventoryList);
    }

    @PostMapping("/inventory")
    public ResponseEntity<Object> addCharacterItem(@RequestBody JSONObject bodyJson) {
        Long charId = Long.parseLong((String) bodyJson.get("characters"));
        Long resId = Long.parseLong((String) bodyJson.get("resource"));
        Integer amount = Integer.parseInt((String)bodyJson.get("amount"));
        Optional<Characters> currentChar = charactersRepository.findById(charId);
        if(!currentChar.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,false,"Character does not exist",currentChar);
        }
        Optional<Resource> currentRes = resourcesRepository.findById(resId);
        if(!currentRes.isPresent()){
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,false,"Resource does not exist",currentRes);
        }
        Characters existingChar = currentChar.get();
        Resource existingRes = currentRes.get();
        if(existingChar.getInventories().stream().anyMatch((inventory -> inventory.getResource().getName().equals(existingRes.getName())))){
            return ResponseHandler.generateResponse(HttpStatus.FORBIDDEN,false,"Use patch route for updating resource amount","");
        }
        Inventory newInventory = new Inventory(currentChar.get(),currentRes.get(),amount);
        Inventory item = inventoryRepository.save(newInventory);
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the character",item);
    }

    @PostMapping("/crafting/{resource}")
    public ResponseEntity<Object> craftItem(@RequestBody JSONObject information, @PathVariable String resource){
        Long char_id = Long.parseLong((String) information.get("characters"));
        Characters character = charactersRepository.findById(char_id).orElse(null);
        Resource resourceToGet = resourcesRepository.findByName(resource);
        try {
            itemsHandler.createItem(character, resourceToGet);
        } catch (IllegalStateException e) {
            return ResponseHandler.generateResponse(HttpStatus.OK,true,"Could not add item to the character", character);
        }
        return ResponseHandler.generateResponse(HttpStatus.OK,true,"Item added to the character", character);
    }
    @CrossOrigin
    @PatchMapping(path = "/inventory", consumes = "application/json")
    public ResponseEntity<Object> patchCharacterItem(@RequestBody JSONObject bodyJson){
//        {
//            "resource":"1",
//            "amount":"2" add to the previous amount
        Long charId = Long.parseLong("1");
        Long resId = Long.parseLong(bodyJson.getAsString("resource"));
        Integer addAmount = Integer.valueOf(bodyJson.getAsString("amount"));

        try {
            List<Inventory> inventories = (List<Inventory>) inventoryRepository.findAll();
            Inventory oldInventory = inventories
                    .stream()
                    .filter((inventory)->inventory.getCharacters().getId().equals(charId) && inventory.getResource().getId().equals(resId))
                    .findFirst().orElseThrow(ChangeSetPersister.NotFoundException::new);

            Integer totalAmount = addAmount + (int)(oldInventory.getAmount());

            String patchJson = "[{ \"op\": \"replace\", \"path\": \"/amount\", \"value\": \""+totalAmount+"\" }]";
            System.out.println(patchJson);

            Inventory inventoryPatched = applyPatchToInventory(patchJson, oldInventory);

            inventoryPatched.setCharacters(oldInventory.getCharacters());
            inventoryPatched.setResource(oldInventory.getResource());
            System.out.println("**************new inventory patch start**************");
            System.out.println(inventoryPatched.getId());
            System.out.println(inventoryPatched.getAmount());
            System.out.println(inventoryPatched.getCharacters());
            System.out.println(inventoryPatched.getResource());
            System.out.println("**************new inventory patch end**************");

            inventoryRepository.save(inventoryPatched);
            return ResponseHandler.generateResponse(HttpStatus.OK,true,"Inventory updated",inventoryPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Inventory applyPatchToInventory(String json, Inventory targetInventory) throws JsonPatchException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream in = new ByteArrayInputStream(json.getBytes());
        final JsonPatch patch = mapper.readValue(in, JsonPatch.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetInventory, JsonNode.class));
        return objectMapper.treeToValue(patched, Inventory.class);
    }

}
