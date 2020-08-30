# BOOK LIBRARY – API     
Overview
A Book Library needs a REST API to be created. The library has a few books and some subscribers. Every time a subscriber walks in to the Library, the Librarian will check the list of Books for availability of copies. Once a Subscriber picks a book a subscription entry will be made by the Librarian to record it.

Database Tables -
The data will be stored in the following two Database tables -

BOOK
BOOK_ID	  BOOK_NAME	                AUTHOR	      AVAILABLE_COPIES	TOTAL_COPIES
B1212	    History of Amazon Valley	Ross Suarez	  0	                2
B4232	    Language Fundamentals	    H S Parkmay	  5	                5


SUBSCRIPTION
SUBSCRIBER_NAME	  DATE_SUBSCRIBED	    DATE_RETURNED	  BOOK_ID
John	            12-JUN-2020		                      B1212
Mark	            26-APR-2020	        14-May-2020	    B4232
Peter	            22-JUN-2020		                      B1212

Three REST APIs (using a SpringBoot application) is created to help the Librarian with his daily functions. The APIs will be used to fetch a list of Books, fetch a list of Subscriptions and create a new Subscription record.

1. URL	GET /books
Response Body ->	Array of Book objects. Each object would contain details of the Book.
Response JSON	[
  {
     bookId : “string”,
     name : “string”,
     author : “string”,
     copiesAvailable : number,
     totalCopies : number
  }
]
Response Http Status code	200

2. URL	GET /subscriptions
Response Body	-> Array of Subscription objects. Each object would contain details of the books subscribed.
Optional Query parameter	subscriberName. 
If provided, show the record for that particular subscriber name.
Response JSON	[
  {
     subscriberName : “string”,
     bookId : “string”,
     dateSubscribed : date,
     dateReturned : date
  }
]
Response Http Status code	200

3. URL	POST /subscriptions
Request Body	-> A single Subscription object.
Request body JSON	
  {
     subscriberName : “string”,
     bookId : “string”,
     dateSubscribed : date,
     dateReturned:date
  }

Response Http Status code	201 – Successful creation of subscription record
422 – If book copies not available for subscription.
Notes	-	Create a record in the SUBSCRIPTION table. For the given bookId check if one or more copies are available in the BOOK table. If not available, then fail the operation. 
-	Use a @Transactional annotation to try the spring managed Commit/Rollback transaction management feature.

##Components

ApiGatewayService - Using Netflix ZUUL proxy
NetflixEurekaService - Using Netflix Eureka discovery client and discovery server.
BookService - Using Spring Boot Rest and Data JPA
SubscriptionService - Using Spring Boot Rest and Data JPA

