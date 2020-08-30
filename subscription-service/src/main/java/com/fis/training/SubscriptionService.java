package com.fis.training;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class SubscriptionService {
	Logger logger = LoggerFactory.getLogger(SubscriptionService.class.getName());
	
	@Autowired
	private BookServiceProxy proxy;
	
	@Autowired
	private SubscriberRepository subscriberRepository;
	
	public ResponseEntity<List<Subscription>> getSubscriptions() {
		List<Subscription> subscribersList = subscriberRepository.findAll();
		if (subscribersList != null)
			return new ResponseEntity<List<Subscription>>(subscribersList, HttpStatus.OK);
		else 
			return new ResponseEntity<List<Subscription>>(subscribersList, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@Transactional
	@HystrixCommand(fallbackMethod="fallbackForSubscription")
	public ResponseEntity<String> subscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateSubscribed) {
		List<Book> books = proxy.getBooks();
		
		for (Book book : books) {
			if (book.getBookId().equalsIgnoreCase(bookId)) {
				if(book.getAvailableCopies() > 0) {
					try {
						proxy.subscribeBook(bookId);
						
						Subscription subscription = new Subscription();
						subscription.setSubscriberName(subscribername);
						subscription.setDateSubscibed(dateSubscribed);
						subscription.setBookId(bookId);
						subscriberRepository.save(subscription);
					}catch(Exception ex) {
						throw new RuntimeException("Book is not available right now, please try later");
					}
				} else {
					throw new RuntimeException("Book is not available right now, please try later");
				}
				break;
			}
		}
		
		return new ResponseEntity<String>("Successful creation of subscription record", HttpStatus.OK);
	}
	
	@Transactional
	@HystrixCommand(fallbackMethod="fallbackForUnsubscription")
	public ResponseEntity<String> unsubscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateUnsubscribed) {
		List<Book> books = proxy.getBooks();
		
		List<Subscription> subscriptionsList = subscriberRepository.findAll();
		outerloop:
		for(Subscription subscription : subscriptionsList) {
			for (Book book : books) {
				if(book.getAvailableCopies() < book.getTotalCopies() && subscription.getBookId().equalsIgnoreCase(bookId) && subscription.getSubscriberName().equalsIgnoreCase(subscribername)) {
					try {
						proxy.unsubscribeBook(bookId);
						subscription.setDateReturned(dateUnsubscribed);
						subscriberRepository.save(subscription);
						break outerloop;
					}catch(Exception ex) {
						throw new RuntimeException("Can't Return the book");
					}
				}
			}
		}
		 
		return new ResponseEntity<String>("Unsubscribed Successfully", HttpStatus.OK);
	}
	
	public ResponseEntity<String> fallbackForSubscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateSubscribed) {
		logger.debug("Book is not available this time, please try later");
		return new ResponseEntity<String>("Book copies not available for subscription", HttpStatus.UNPROCESSABLE_ENTITY);
    }
	
	public ResponseEntity<String> fallbackForUnsubscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateUnsubscribed) {
		logger.debug("Either this user is not associated with this book or already unscribed");
		return new ResponseEntity<String>("Unsubsciption can not be possible", HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
