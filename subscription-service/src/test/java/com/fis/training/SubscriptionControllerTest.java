package com.fis.training;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SubscriptionControllerTest {

	@Autowired 
	SubscriptionController controller;
	
	@Test
	public void getBooksTest() {
		ResponseEntity<List<Subscription>> bookList = controller.getSubscriptions();
		assertThat(bookList.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(bookList.getBody()).isNotNull();
	}
	
	@Test
	public void subscribeBookTest() {
		ResponseEntity<String> serviceResponse = controller.subscription("suneeta", "B4232", "28-08-2020");
		assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(serviceResponse.getBody()).isEqualTo("Successful creation of subscription record");
	}
	
	@Test
	public void unsubscribeBookTest() {
		ResponseEntity<String> serviceResponse = controller.unsubscription("suneeta", "B4232", "28-08-2020");
		assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(serviceResponse.getBody()).isEqualTo("Unsubscribed Successfully");
	}
}
