package com.udacity.course3.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Document("comment")
public class CommentMDBDocument {

    @Transient
    public static final String SEQUENCE_NAME ="comments_sequence";

    @Id
    private Long id;
    private String comment;

    private Long relatedReviewId;

    public CommentMDBDocument(String comment) {
        this.comment = comment;
    }
}
