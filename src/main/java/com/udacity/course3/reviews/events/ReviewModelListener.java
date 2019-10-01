package com.udacity.course3.reviews.events;

import com.udacity.course3.reviews.model.CommentMDBDocument;
import com.udacity.course3.reviews.model.ReviewMDBDocument;
import com.udacity.course3.reviews.service.MongoDBSaveService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ReviewModelListener extends AbstractMongoEventListener<ReviewMDBDocument> {

    private MongoDBSaveService mongoDBSaveService;

    public ReviewModelListener(MongoDBSaveService mongoDBSaveService) {
        this.mongoDBSaveService = mongoDBSaveService;
    }

    public void onBeforeConvert2(BeforeConvertEvent<CommentMDBDocument> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(mongoDBSaveService.genReviewSequence(ReviewMDBDocument.SEQUENCE_NAME));
        }
    }

    public void onBeforeConvert1(BeforeConvertEvent<CommentMDBDocument> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(mongoDBSaveService.genCommentSequence(CommentMDBDocument.SEQUENCE_NAME));
        }
    }
}
