package com.fis.training;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> bookList = bookRepository.findAll();
		if (bookList != null)
			return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
		else
			return new ResponseEntity<List<Book>>(bookList, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@Transactional
	@HystrixCommand(fallbackMethod="fallbackForSubscribeBook")
	public ResponseEntity<String> subscribeBook(@RequestParam String bookId) {
		List<Book> bookList = bookRepository.findAll();
		
		for (Book book : bookList) {
			if (book.getBookId().equalsIgnoreCase(bookId)) {
				if(book.getAvailableCopies() > 0) {
					book.setAvailableCopies(book.getAvailableCopies()-1);
					bookRepository.save(book);
				} else {
					throw new RuntimeException("Requested book not available");
				}
				break;
			}
		}
		return new ResponseEntity<String>("Book Subscribed Successfully", HttpStatus.OK);
	}
	
	@Transactional
	@HystrixCommand(fallbackMethod="fallbackForUnSubscribeBook")
	public ResponseEntity<String> unsubscribeBook(@RequestParam String bookId) {
		List<Book> bookList = bookRepository.findAll();
		
		for (Book book : bookList) {
			if (book.getBookId().equalsIgnoreCase(bookId)) {
				if(book.getAvailableCopies() < book.getTotalCopies()) {
					book.setAvailableCopies(book.getAvailableCopies()+1);
					bookRepository.save(book);
				} else {
					throw new RuntimeException("Can't be unsubscribed the requested book");
				}
				break;
			}
		}
		
		return new ResponseEntity<String>("Book UnSubscribed Successfully", HttpStatus.OK);
	}
	
	public ResponseEntity<String> fallbackForSubscribeBook(@PathVariable String bookId) {
        return new ResponseEntity<String>("BookId = " + bookId + " is not available right now, please check later",
        		HttpStatus.UNPROCESSABLE_ENTITY);
    }
	
	public ResponseEntity<String> fallbackForUnSubscribeBook(@PathVariable String bookId) {
        return new ResponseEntity<String>("Book = " + bookId + " is already unscriberd by all subscribers",
        		HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
