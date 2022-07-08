package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
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

    @Test
    public void testCreateCharacter() throws Exception{
        URI uri = new URI("http://localhost:"+randomServerPort+"/characters");
        ResponseEntity<String> post = restTemplate.postForEntity(uri, testCharacter, String.class);
        assertThat(post.getBody().contains("\"id\":1"));
        assertThat(post.getBody().contains("\"name\":\"TestCharacter\""));
        assertThat(post.getBody().contains("\"mining\":0"));
        System.out.println(post.getBody());
        assertThat(Long.valueOf(1)).isEqualTo(charactersRepository.findAll().get(0).getId());
        assertThat("TestCharacter").isEqualTo(charactersRepository.findAll().get(0).getName());
        assertThat((Integer)0).isEqualTo(charactersRepository.findAll().get(0).getMining());
    }

    @Test
    public void testGetCharactersDetails() throws URISyntaxException {
        charactersRepository.save(testCharacter);
        URI uri = new URI("http://localhost:"+randomServerPort+"/characters");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        assertThat(response.getBody().toString()).contains("\"id\":1");
        assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
        assertThat(response.getBody().toString()).contains("\"mining\":0");
        // Example of a json body
        //"{"data":{"id":1,"name":"TestCharacter","mining":0},"message":"The character is returned","timestamp":"2022-07-06T14:34:12.712+00:00","status":200,"isSuccess":true}"
    }
    //@Ignore
    @Test
    public void testUpdateExperience() throws URISyntaxException {
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
}