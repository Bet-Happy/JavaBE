package com.bethappy.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isSuccess, String message,
                                                          Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", isSuccess);
            map.put("message", message);
            map.put("data", responseObj);
            //pass another map (map in a map) as a response object for the xp? (mining:1111, smithing:2222)
            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map, status);
        }
    }
}
