package com.udacity.course3.reviews.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "comment_sequence")
public class CommentSequenceObject {



    @Id
    private String id;
    private Long commentSequence;

    public CommentSequenceObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCommentSequence() {
        return commentSequence;
    }

    public void setCommentSequence(Long commentSequence) {
        this.commentSequence = commentSequence;
    }
}
