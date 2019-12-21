package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.utils.HashUtility;

@SpringBootTest
class JunitExampleApplicationTests {

	@Autowired
	HashUtility hash;

	@Test
	void testMethod() {

		// Using asserts method we can test against the expected output
		assertNotNull(hash.generateHash("Tarun"));
	}

	@Test
	void checkResponseOfAPI() {
		RestTemplate restTemplate = new RestTemplate();

		// Using asserts method we can test against the expected output of API
		
		String name = "Tarun";
		
		final String baseUrl = "http://localhost:8080/getUser/" + name;
		URI uri = null;
		try {
			uri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		assertEquals(200,result.getStatusCodeValue());
		System.out.println(result.getBody());
		assertEquals("Tarun", result.getBody());
	}
}
