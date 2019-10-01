package com.udacity.course3.reviews.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "review_sequence")
public class ReviewSequenceObject {



    @Id
    private String id;
    private Long reviewSequence;

    public ReviewSequenceObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getReviewSequence() {
        return reviewSequence;
    }

    public void setReviewSequence(Long reviewSequence) {
        this.reviewSequence = reviewSequence;
    }
}
