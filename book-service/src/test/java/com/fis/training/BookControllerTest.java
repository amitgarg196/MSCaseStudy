package com.fis.training;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookControllerTest {

	@Autowired 
	BookController controller;
	
	@Test
	public void getBooksTest() {
		ResponseEntity<List<Book>> bookList = controller.getBooks();
		assertThat(bookList.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(bookList.getBody()).isNotNull();
	}
	
	@Test
	public void subscribeBookTest() {
		ResponseEntity<String> serviceResponse = controller.subscribeBook("B4232");
		assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(serviceResponse.getBody()).isEqualTo("Book Subscribed Successfully");
	}
	
	@Test
	public void unsubscribeBookTest() {
		ResponseEntity<String> serviceResponse = controller.unsubscribeBook("B4232");
		assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(serviceResponse.getBody()).isEqualTo("Book UnSubscribed Successfully");
	}
}
