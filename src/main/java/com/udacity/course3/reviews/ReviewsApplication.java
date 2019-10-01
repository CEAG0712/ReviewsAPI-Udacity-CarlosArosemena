package com.udacity.course3.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ReviewsApplication {


	@Autowired
	private MongoTemplate mongoTemplate;
	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}



}