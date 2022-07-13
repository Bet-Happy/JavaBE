package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.model.Inventory;
import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.CharactersRepository;
import com.bethappy.demo.repository.InventoryRepository;
import com.bethappy.demo.repository.ResourcesRepository;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
    private RestTemplate patchRestTemplate;

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
    public void addAnItem(){
        //need to add an item here
    }

    @Before
    public void setup() {

        // Add Apache HttpClient as TestRestTemplate
        this.patchRestTemplate = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
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
    @Test
    public void patchAnItem () throws JSONException {
        emptyInventoryTable();
        emptyCharacterTable();
        addACharacter();
        addResources();
        addAnItem();

        String resourceUrl = "/inventory/1";

//         Create JSON Object
//         {
//         "resource": "1",
//         "amount": 10
//         }
        JSONObject updateBody = new JSONObject();
        updateBody.put("resource", "1");
        updateBody.put("amount", 10);

        // STEP #3
        // Use patchRestTemplate to make call with PATCH method
        ResponseEntity responseEntity = patchRestTemplate.exchange(resourceUrl, HttpMethod.PATCH, getPostRequestHeaders(updateBody.toString()), Inventory.class);

        // Ensure Status Code is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Ensure Content-Type is application/json
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        // Ensure that PATCH updated firstname
        // from "NotRyan" to "Ryan"
        Inventory updatedContact = (Inventory) responseEntity.getBody();
        assertThat( updatedContact.getResource().getName()).isEqualTo("Copper");
        assertThat(updatedContact.getAmount()).isEqualTo(10);
    }

    public HttpEntity getPostRequestHeaders(String jsonPostBody) {
        List acceptTypes = new ArrayList();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity(jsonPostBody, reqHeaders);
    }
}
