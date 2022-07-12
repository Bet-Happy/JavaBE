package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class InventoryControllerTests {
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    CharactersRepository charactersRepository;
    @Autowired
    ResourcesRepository resourcesRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    public void emptyInventoryTable(){
        inventoryRepository.deleteAll();
    }
    public void emptyCharacterTable(){
        charactersRepository.deleteAll();
    }
    public void emptyResourceTable(){
        resourcesRepository.deleteAll();
    }
    public void addACharacter(){
        Characters newChar = new Characters(Long.valueOf("1"),"TestUser1");
        charactersRepository.save(newChar);
    }
    public void addResources(){
        Resource newRes = new Resource (Long.parseLong("1"),"Copper",5,5);
        resourcesRepository.save(newRes);
    }


    @Test
    public void getAllInventoryDetails() throws URISyntaxException {
        emptyInventoryTable();
        URI uri = new URI("http://localhost:"+randomServerPort+"/inventory");
        //create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json;charset=utf-8");
        ResponseEntity<String> response = this.restTemplate.getForEntity(uri,String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"message\":\"InventoryList retrieved\"");
        assertThat(response.getBody()).contains("\"data\":[]");
    }
    @Test
    public void addAItemToAnInvalidCharacter ()throws URISyntaxException {
        emptyInventoryTable();
        emptyCharacterTable();
        addResources();
        URI uri = new URI("http://localhost:"+randomServerPort+"/inventory");
        //create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        JSONObject inventoryJsonObject = new JSONObject();
        try {

            inventoryJsonObject.put("id", "1");
            inventoryJsonObject.put("characters", "1");
            inventoryJsonObject.put("resource", "1");
            inventoryJsonObject.put("amount", "2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //httpEntity
        HttpEntity<String> request = new HttpEntity<>(inventoryJsonObject.toString(),headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(uri,request,String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("\"message\":\"Character does not exist\"");
        assertThat(response.getBody()).contains("\"data\":null");
    }
    @Test
    public void addAItemToAValidCharacter ()throws URISyntaxException {
        emptyInventoryTable();
        emptyCharacterTable();
        addACharacter();
        addResources();
        URI uri = new URI("http://localhost:"+randomServerPort+"/inventory");
        //create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        JSONObject inventoryJsonObject = new JSONObject();
        try {

            inventoryJsonObject.put("id", "1");
            inventoryJsonObject.put("characters", "1");
            inventoryJsonObject.put("resource", "1");
            inventoryJsonObject.put("amount", "2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //httpEntity
        HttpEntity<String> request = new HttpEntity<>(inventoryJsonObject.toString(),headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(uri,request,String.class);
        System.out.printf(response.getBody().toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"message\":\"Item added to the character\"");
        assertThat(response.getBody()).contains("{\"data\":{\"id\":1,\"amount\":2}");
    }
}
