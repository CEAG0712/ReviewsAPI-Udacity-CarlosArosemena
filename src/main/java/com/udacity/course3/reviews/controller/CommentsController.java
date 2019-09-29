package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.exceptions.ItemNotFoundException;
import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here

    private CommentRepository commentRepository;
    private ReviewRepository reviewRepository;
    private ValidationService validationService;

    public CommentsController(CommentRepository commentRepository, ReviewRepository reviewRepository, ValidationService validationService) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.validationService = validationService;
    }

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @PostMapping("/reviews/{reviewId}")
    public ResponseEntity createCommentForReview(@PathVariable("reviewId") Long reviewId,
                                                    @Valid @RequestBody Comment comment, BindingResult result) {

        ResponseEntity errorMap = validationService.validationService(result);
        if(errorMap!=null) return errorMap;

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ItemNotFoundException("Review not found"));

        comment.setReview(review);

        return ResponseEntity.ok().body(commentRepository.save(comment));

    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity listCommentsForReview(@PathVariable("reviewId") Long reviewId) {
        reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ItemNotFoundException("Review not found, No comment list to be displayed"));
        return ResponseEntity.ok().body(commentRepository.findAllByReviewId(reviewId));
    }
}