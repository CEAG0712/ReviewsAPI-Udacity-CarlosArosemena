package com.udacity.course3.reviews.service;


import com.udacity.course3.reviews.model.*;
import com.udacity.course3.reviews.repository.CommentMongoRepo;
import com.udacity.course3.reviews.repository.ReviewMongoRepo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

//This class is aimed to provide an auto increment for Review and Comment Id
@Service
public class MongoDBSaveService {

    private ReviewMongoRepo reviewMongoRepo;
    private CommentMongoRepo commentMongoRepo;
    private MongoOperations mongoOperations;

    public MongoDBSaveService(ReviewMongoRepo reviewMongoRepo, CommentMongoRepo commentMongoRepo, MongoOperations mongoOperations) {
        this.reviewMongoRepo = reviewMongoRepo;
        this.commentMongoRepo = commentMongoRepo;
        this.mongoOperations = mongoOperations;
    }

    public Long genReviewSequence(String sequence){
        ReviewSequenceObject counter = mongoOperations.findAndModify(query(where("id").is(sequence)),
                new Update().inc("reviewSequence",1), options().returnNew(true).upsert(true),
                ReviewSequenceObject.class);
        return !Objects.isNull(counter) ? counter.getReviewSequence() : 1;
    }

    public Long genCommentSequence(String sequence){
        CommentSequenceObject counter = mongoOperations.findAndModify(query(where("id").is(sequence)),
                new Update().inc("commentSequence",1), options().returnNew(true).upsert(true),
                CommentSequenceObject.class);
        return !Objects.isNull(counter) ? counter.getCommentSequence() : 1;
    }

    public ReviewMDBDocument saveMongoReview(ReviewMDBDocument reviewMDBDocument){
        reviewMDBDocument.setId(genReviewSequence(ReviewMDBDocument.SEQUENCE_NAME));

        return reviewMongoRepo.save(reviewMDBDocument);
    }

    public CommentMDBDocument saveMongoComment(CommentMDBDocument commentMDBDocument){
        commentMDBDocument.setId(genCommentSequence(CommentMDBDocument.SEQUENCE_NAME));
        return commentMongoRepo.save(commentMDBDocument);
    }
}


