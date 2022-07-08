package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;

import org.apache.catalina.mapper.Mapper;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
//
import org.mockito.Mock;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
    URI uri = new URI("http://localhost:"+randomServerPort+"/character");
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
    URI uri = new URI("http://localhost:"+randomServerPort+"/character");
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
    URI uri = new URI("http://localhost:"+randomServerPort+"/character/updateExperience");



    HttpHeaders reqHeaders = new HttpHeaders();
    reqHeaders.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestEntity = new HttpEntity<String>("{\"id\": 1,\"mining\":100}", reqHeaders);
    ResponseEntity<String> patch = restTemplate.exchange(uri, HttpMethod.PATCH, requestEntity, String.class); 



    //testCharacter = restTemplate.patchForObject(uri, post, String.class);
    assertThat(patch.getBody().toString()).contains("\"mining\":100");
    // assertThat(response.getBody().toString()).contains("\"name\":\"TestCharacter\"");
    // assertThat(response.getBody().toString()).contains("\"mining\":0");
  }
}

