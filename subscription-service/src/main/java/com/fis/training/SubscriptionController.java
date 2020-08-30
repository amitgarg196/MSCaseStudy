package com.fis.training;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

@RestController
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping("/subscriptions")
	public ResponseEntity<List<Subscription>> getSubscriptions() {
		return subscriptionService.getSubscriptions();
	}

	@PostMapping("/subscription")
	public ResponseEntity<String> subscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateSubscribed) {
		return subscriptionService.subscription(subscribername, bookId, dateSubscribed);
	}
	
	@PostMapping("/unsubscription")
	public ResponseEntity<String> unsubscription(@RequestParam String subscribername, @RequestParam String bookId, @RequestParam String dateUnsubscribed) {
		return subscriptionService.unsubscription(subscribername, bookId, dateUnsubscribed);
	}
}