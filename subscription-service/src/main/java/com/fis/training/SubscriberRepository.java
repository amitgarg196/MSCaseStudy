package com.fis.training;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SubscriberRepository extends CrudRepository<Subscription, Long> {
	@Override
    List<Subscription> findAll();
}