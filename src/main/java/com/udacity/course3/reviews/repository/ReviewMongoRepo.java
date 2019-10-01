package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.ReviewMDBDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewMongoRepo extends MongoRepository<ReviewMDBDocument, Long> {

    List<ReviewMDBDocument> findAllByRelatedProductId(Long id);
    //It is mandatory to specify the list type for mongo, I was getting
    /*
    org.springframework.data.mapping.MappingException: Couldn't find PersistentEntity for type class java.lang.Object!

    when my implementation was like this:  List findAllByRelatedProductId(Long id);
    which works in relational DB
     */
}
