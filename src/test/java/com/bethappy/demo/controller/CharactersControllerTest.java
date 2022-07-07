package com.bethappy.demo.controller;

import com.bethappy.demo.model.Characters;
import com.bethappy.demo.repository.CharactersRepository;
import org.junit.Test;
//
import org.junit.jupiter.api.BeforeAll;
//
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
// NOT USED ATM 06/07/2022
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import  static org.hamcrest.CoreMatchers.containsString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.server.LocalServerPort;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CharactersControllerTest {
  private final Characters testCharacter = new Characters("TestCharacter");
   @Autowired
   CharactersRepository charactersRepository;
   @Autowired
   private TestRestTemplate restTemplate;

   @LocalServerPort
   int randomServerPort;

  @BeforeAll
  public void addCharacter() {
    charactersRepository.save(testCharacter);
  }
   @Test
   public void getCharactersDetails() throws URISyntaxException {
       URI uri = new URI("http://localhost:"+randomServerPort+"/");
      ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody().toString()).isEqualTo("{\"mining:0");
   }
}

