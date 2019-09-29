package com.udacity.course3.reviews.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "please include the review summary")
    private String reviewSummary;

    @NotBlank(message = "please enter your comments")
    @Size(max = 250, message = "Your comments must not exceed 250 characters")
    private String reviewDescription;

    //Set up an enum on the DB. I was really curious. it works
    //    review_rating ENUM('GREAT', 'GOOD', 'AVERAGE', 'POOR', 'UNACCEPTABLE'),
    private String reviewRating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "review")
    private List<Comment> commentList = new ArrayList<>();

    //Constructor w/o the ID and Comment List


    public Review(@NotBlank(message = "please include the review summary") String reviewSummary, @NotBlank(message = "please enter your comments") @Size(max = 250, message = "Your comments must not exceed 250 characters") String reviewDescription, String reviewRating) {
        this.reviewSummary = reviewSummary;
        this.reviewDescription = reviewDescription;
        this.reviewRating = reviewRating;
    }


}
