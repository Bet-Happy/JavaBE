//package com.bethappy.demo.controller;
//
//import com.bethappy.demo.model.Characters;
//import com.bethappy.demo.repository.CharactersRepository;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//public class CharactersControllerTests {
////    @Autowired
////    CharactersRepository charactersRepository;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @LocalServerPort
//    int randomServerPort;
//
//    public void getCharactersDetails() throws URISyntaxException {
//        URI uri = new URI("http://localhost:"+randomServerPort+"/characters");
//        Characters charBobby = new Characters("Bobby");
////        //create headers
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type","application/json;charset=utf-8");
////        //httpEntity
////        HttpEntity<String> request = new HttpEntity<>("",headers);
//        ResponseEntity<String> response = this.restTemplate.getForEntity(uri,String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//}
