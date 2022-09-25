package com.appollo.api_controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 
 * A test endpoint to show if application is running
 */

@RestController
public class EnvironmentTestController {
   @Value("${message}")
   String message;
   
   @GetMapping(value = "EnvironmentTest")
   public ResponseEntity<String> displayHelloMessage() {
      return ResponseEntity.ok(message);
   }
   
  
   
}
