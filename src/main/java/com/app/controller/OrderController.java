package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class OrderController {
	
	private static final String exchange_service = "currency-exchange-service";
	
	@GetMapping("/exchange")
	@CircuitBreaker(name = "exchange_service",fallbackMethod = "exchangeFallback")
	public ResponseEntity<String> createOrder(){		
		String response =
				new RestTemplate().getForObject("http://localhost:8000/currency-exchange/from/USD/to/OMR", String.class);
		
		return new ResponseEntity<String>(response,HttpStatus.OK);		
		
	}
	
	public ResponseEntity<String> exchangeFallback(Exception e){
		return new ResponseEntity<String>("SERVICE IS DOWN!! TRY AFTER SOME TIME", HttpStatus.OK);
	}

}
