package com.fis.training;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String subscriberName;
	private String dateSubscribed;
	private String dateReturned;
	private String bookId;
	
	public Subscription(Long id, String subscriberName, String dateSubscibed, String dateReturned, String bookId) {
		super();
		this.id = id;
		this.subscriberName = subscriberName;
		this.dateSubscribed = dateSubscibed;
		this.dateReturned = dateReturned;
		this.bookId = bookId;
	}

	public Subscription() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getDateSubscibed() {
		return dateSubscribed;
	}

	public void setDateSubscibed(String dateSubscibed) {
		this.dateSubscribed = dateSubscibed;
	}

	public String getDateReturned() {
		return dateReturned;
	}

	public void setDateReturned(String dateReturned) {
		this.dateReturned = dateReturned;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
}