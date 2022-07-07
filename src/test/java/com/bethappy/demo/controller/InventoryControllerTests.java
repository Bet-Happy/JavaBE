package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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
    static
    CharactersRepository characterRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @BeforeAll
    static void createCharacter(){
        Characters testUser1 = new Characters("TestUser1");
        characterRepository.save (testUser1);
    }

    @Test
    public void getAllInventoryDetails() throws URISyntaxException {
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
    public void addAItemToInventory ()throws URISyntaxException {
        URI uri = new URI("http://localhost:"+randomServerPort+"/inventory");
        //create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        JSONObject personJsonObject = new JSONObject();
        try {
            personJsonObject.put("character", "1");
            personJsonObject.put("slot_number", "1");
            personJsonObject.put("amount", "2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //httpEntity
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString());
        ResponseEntity<String> response = this.restTemplate.postForEntity(uri,request,String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"message\":\"InventoryList retrieved\"");
        assertThat(response.getBody()).contains("\"data\":[]");
    }
}
