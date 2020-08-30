package com.fis.training;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="api-gateway-service")
@RibbonClient(name="book-service")
public interface BookServiceProxy {
     @GetMapping("/book-service/books")
     public List<Book> getBooks();
     
     @PostMapping("/book-service/subscribeBook")
     public String subscribeBook(@RequestParam String bookId);
     
     @PostMapping("/book-service/unsubscribeBook")
     public String unsubscribeBook(@RequestParam String bookId);
}
