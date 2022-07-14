package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import org.junit.Test;
//
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import  static org.hamcrest.CoreMatchers.containsString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.mockito.Mock;

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

  @BeforeAll
  public void addCharacter() {
    // Integrate this later on
  }
  
  @Test
  public void TestCreateCharacter() throws Exception{
    URI uri = new URI("http://localhost:"+randomServerPort+"/character");
    ResponseEntity<String> post = restTemplate.postForEntity(uri, testCharacter, String.class);
    assertThat(post.getBody().contains("\"id\":1"));
    assertThat(post.getBody().contains("\"name\":\"TestCharacter\""));
    assertThat(post.getBody().contains("\"mining\":0"));
    assertThat(Long.valueOf(1)).isEqualTo(charactersRepository.findAll().get(0).getId());
    assertThat("TestCharacter").isEqualTo(charactersRepository.findAll().get(0).getName());
    assertThat((Integer)0).isEqualTo(charactersRepository.findAll().get(0).getMining());
  }
  
  @Test
  public void TestGetCharactersDetails() throws URISyntaxException {
    charactersRepository.save(testCharacter);
    URI uri = new URI("http://localhost:"+randomServerPort+"/character");
    System.out.println(charactersRepository.findAll().get(0).getId()+"NAME:"+charactersRepository.findAll().get(0).getName());
    ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

    assertThat(response.getBody().toString()).contains("\"id\":1");
    assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
    assertThat(response.getBody().toString()).contains("\"mining\":0");
    // Example of a json body
    //"{"data":{"id":1,"name":"TestCharacter","mining":0},"message":"The character is returned","timestamp":"2022-07-06T14:34:12.712+00:00","status":200,"isSuccess":true}"
    }
}

