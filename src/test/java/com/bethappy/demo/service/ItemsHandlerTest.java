package com.bethappy.demo.service;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.service.ItemsHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
//
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
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
    //create an inventory here
    System.out.println("////\n//\n");
    handler.createItem(inventoryTestCharacter, resource);
    //System.out.println(inventoryRepository.findByCharacters_Id(inventoryTestCharacter.getId()));
    
    //handler.createItem(inventoryTestCharacter, resource);
  }
}
