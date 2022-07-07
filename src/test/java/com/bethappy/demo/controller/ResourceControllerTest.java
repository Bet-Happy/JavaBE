package com.bethappy.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ResourceControllerTest {
  
@Test

public void getResourcesStatus()
throws Exception {
  HttpClient client = HttpClient.newBuilder().build();
  HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/resources")).build();
  
  HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
  assertThat(response.statusCode()).isEqualTo(200);
  // System.out.println(response);
  // System.out.println(request);
  // System.out.println(BodyHandlers.ofString());
}

@Test

public void getResourcesEntity()
throws Exception {
  HttpClient client = HttpClient.newBuilder().build();
  HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/resources")).build();
  
  HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
  // System.out.println(response.body());
  assertTrue(response.body().contains("\"name\":\"Copper\""));
  // assertThat(response.body).isEqualTo;
  // System.out.println(response);
  // System.out.println(request);
  // System.out.println(BodyHandlers.ofString());
}
}
