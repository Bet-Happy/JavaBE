package com.bethappy.demo.controller;

import java.io.IOException;
import java.net.URI;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse.BodyHandlers;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bethappy.demo.model.Resource;
import com.bethappy.demo.repository.ResourcesRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResourcesControllerTest {
  @Autowired
  ResourcesRepository resourcesRepository;
  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  int randomServerPort;
  
 
  @Test
  public void TestResourcesEntity() throws Exception {
    URI uri = new URI("http://localhost:"+randomServerPort+"/resources");
    ResponseEntity<String> get = restTemplate.getForEntity(uri,String.class);
    assertThat(get.getBody().contains("\"name\":\"Copper\""));

  }

  @Test
  public void TestResourcesStatus() throws Exception {
    URI uri = new URI("http://localhost:"+randomServerPort+"/resources");
    ResponseEntity<String> get = restTemplate.getForEntity(uri, String.class);
    assertTrue(get.getStatusCodeValue() == 200);

  }

  @Test
  public void TestGetSingleResource() throws Exception {
    URI uri = new URI("http://localhost:"+randomServerPort+"/resources/Copper");
    ResponseEntity<String> get = restTemplate.getForEntity(uri, String.class);
    assertThat(get.getBody().contains("\"name\":\"Copper\""));
  }

  @Test
  public void TestSingleResourcesStatus() throws Exception {
    URI uri = new URI("http://localhost:"+randomServerPort+"/resources/Copper");
    ResponseEntity<String> get = restTemplate.getForEntity(uri, String.class);
    assertTrue(get.getStatusCodeValue() == 200);

  }
}
