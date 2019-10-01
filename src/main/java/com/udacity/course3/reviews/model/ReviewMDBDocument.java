package com.udacity.course3.reviews.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document("review")
@Data
public class ReviewMDBDocument {

    @Transient
    public static final String SEQUENCE_NAME ="reviews_sequence";

    @Id
    private Long id;

    @NotBlank(message = "please include the review summary")
    private String reviewSummary;

    @NotBlank(message = "please enter your comments")
    @Size(max = 250, message = "Your comments must not exceed 250 characters")
    private String reviewDescription;

    private String reviewRating;

    private Long relatedProductId;

    @Embedded
    private List<CommentMDBDocument> commentList = new ArrayList<>();

    public ReviewMDBDocument(@NotBlank(message = "please include the review summary") String reviewSummary, @NotBlank(message = "please enter your comments") @Size(max = 250, message = "Your comments must not exceed 250 characters") String reviewDescription, String reviewRating) {
        this.reviewSummary = reviewSummary;
        this.reviewDescription = reviewDescription;
        this.reviewRating = reviewRating;
    }


}
