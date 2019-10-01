package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.CommentMDBDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentMongoRepo extends MongoRepository<CommentMDBDocument,Long> {

    List<CommentMDBDocument> findAllByRelatedReviewId(Long id);
}
