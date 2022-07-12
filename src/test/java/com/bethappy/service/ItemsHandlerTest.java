package com.bethappy.service;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.model.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.bethappy.demo.service.ItemsHandler;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
//
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ItemsHandlerTest {

  private final Characters inventoryTestCharacter = new Characters("InventoryTestCharacter");
  private final Resource resource = new Resource("Copper", 5, 5);
  @Autowired
  CharactersRepository charactersRepository;
  @Autowired
  InventoryRepository inventoryRepository;

  @Test
  public void testGetInventoryId(){
    ItemsHandler handler = new ItemsHandler();
    charactersRepository.save(inventoryTestCharacter);
    System.out.println("////\n//\n");
    ItemsHandler.createItem(inventoryTestCharacter, resource);
    //System.out.println(inventoryRepository.findByCharacters_Id(inventoryTestCharacter.getId()));
    
    //handler.createItem(inventoryTestCharacter, resource);
  }
}
