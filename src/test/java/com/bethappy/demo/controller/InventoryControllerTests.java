package com.bethappy.demo.controller;

import com.bethappy.demo.repository.InventoryRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    public void EmptyInventoryTable(){
        inventoryRepository.deleteAll();
    }

    @Test
    public void getAllInventoryDetails() throws URISyntaxException {
        EmptyInventoryTable();
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
        EmptyInventoryTable();
        URI uri = new URI("http://localhost:"+randomServerPort+"/inventory");
        //create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        JSONObject personJsonObject = new JSONObject();
        try {
            personJsonObject.put("characters", "1");
            personJsonObject.put("slot_number", "1");
            personJsonObject.put("amount", "2");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //httpEntity
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(),headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(uri,request,String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"message\":\"Item added to the inventory\"");
        assertThat(response.getBody()).contains("{\"data\":{\"id\":1,\"characters\":{");
        assertThat(response.getBody()).contains("\"mining\":0},\"slot_number\":1,\"amount\":2}");
    }
}
