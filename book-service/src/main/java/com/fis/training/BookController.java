package com.fis.training;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		return bookService.getBooks();
	}
	
	@PostMapping("/subscribeBook")
	public ResponseEntity<String> subscribeBook(@RequestParam String bookId) {
		return bookService.subscribeBook(bookId);
	}
	
	@PostMapping("/unsubscribeBook")
	public ResponseEntity<String> unsubscribeBook(@RequestParam String bookId) {
		return bookService.unsubscribeBook(bookId);
	}
}