package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
//
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CharactersControllerTest {
    private final Characters testCharacter = new Characters("TestCharacter");
    private final Characters testCharacter2 = new Characters("TestCharacter2");
    @Autowired
    CharactersRepository charactersRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;
    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeAll
    public void addCharacter() {
        // Integrate this later on
    }
    //@Ignore
    @Test
    // Does not test multiple characters
    public void testCreatesAndReturnsCharacters() throws Exception{
        URI uri = new URI("http://localhost:"+randomServerPort+"/characters");
        ResponseEntity<String> post = restTemplate.postForEntity(uri, testCharacter, String.class);
        assertThat(post.getBody().contains("\"id\":1"));
        assertThat(post.getBody().contains("\"name\":\"TestCharacter\""));
        assertThat(post.getBody().contains("\"mining\":0"));
        assertThat(post.getBody().contains("\"smithing\":0"));
        // Should I mock the timestamp somehow? I need to pass the value to the constructor for that 
        // and it seems unnecessary to do just for the tests
        assertThat(post.getBody().contains("\"creation\":"));

        post = restTemplate.postForEntity(uri, testCharacter2, String.class);
        assertThat(post.getBody().contains("\"id\":2"));
        assertThat(post.getBody().contains("\"name\":\"TestCharacter2\""));
        assertThat(post.getBody().contains("\"mining\":0"));
        assertThat(post.getBody().contains("\"smithing\":0"));
        assertThat(post.getBody().contains("\"creation\":"));

        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        assertThat(response.getBody().toString()).contains("\"id\":1");
        assertThat(response.getBody().toString()).contains("\"id\":2");
        assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
        assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter2\"");
        assertThat(response.getBody().toString()).contains("\"mining\":0");
        assertThat(response.getBody().toString()).contains("\"smithing\":0");
        assertThat(response.getBody().toString()).contains("\"creation\":");
        // Example of a json body
        // "{"data":{"id":1,"name":"TestCharacter","mining":0},"message":"The character is returned",
        // "timestamp":"2022-07-06T14:34:12.712+00:00","status":200,"isSuccess":true}"
    }

    //@Ignore
    @Test
    public void testUpdateMiningExperience() throws URISyntaxException {
        charactersRepository.save(testCharacter);
        URI uri = new URI("http://localhost:"+randomServerPort+"/characters/updateExperience");
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>("{\"id\":1,\"mining\":100}", reqHeaders);
        ResponseEntity<String> post = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        assertThat(post.getBody().toString()).contains("\"id\":1");
        assertThat(post.getBody().toString()).contains("\"mining\":100");
        uri = new URI ("http://localhost:"+randomServerPort+"/characters");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        assertThat(response.getBody().toString()).contains("\"id\":1");
        assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
        assertThat(response.getBody().toString()).contains("\"mining\":100");
    }
    @Test
    public void testUpdateSmithingExperience() throws URISyntaxException {
        charactersRepository.save(testCharacter);
        URI uri = new URI("http://localhost:"+randomServerPort+"/characters/updateExperience");
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        // If it is set to mining instead of smithing in the requestEntity, it works. With smithing it does not
        HttpEntity<String> requestEntity = new HttpEntity<String>("{\"id\":1,\"smithing\":100}", reqHeaders);
        ResponseEntity<String> post = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        System.out.println("//////////\n"+post+"\n////////////");
        assertThat(post.getBody().toString()).contains("\"id\":1");
        assertThat(post.getBody().toString()).contains("\"smithing\":100");
        uri = new URI ("http://localhost:"+randomServerPort+"/characters");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        assertThat(response.getBody().toString()).contains("\"id\":1");
        assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
        assertThat(response.getBody().toString()).contains("\"smithing\":100");
    }
}