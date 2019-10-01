package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.exceptions.ItemNotFoundException;
import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.CommentMDBDocument;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.model.ReviewMDBDocument;
import com.udacity.course3.reviews.repository.*;
import com.udacity.course3.reviews.service.MongoDBSaveService;
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

    private MongoDBSaveService mongoDBSaveService;
    private ReviewMongoRepo reviewMongoRepo;
    private CommentMongoRepo commentMongoRepo;
    private ValidationService validationService;

    public CommentsController(MongoDBSaveService mongoDBSaveService, ReviewMongoRepo reviewMongoRepo, CommentMongoRepo commentMongoRepo, ValidationService validationService) {
        this.mongoDBSaveService = mongoDBSaveService;
        this.reviewMongoRepo = reviewMongoRepo;
        this.commentMongoRepo = commentMongoRepo;
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
                                                 @Valid @RequestBody CommentMDBDocument comment, BindingResult result) {

        ResponseEntity errorMap = validationService.validationService(result);
        if(errorMap!=null) return errorMap;

        ReviewMDBDocument review = reviewMongoRepo.findById(reviewId)
                .orElseThrow(()-> new ItemNotFoundException("Review not found"));

        comment.setRelatedReviewId(reviewId);

        return ResponseEntity.ok().body(mongoDBSaveService.saveMongoComment(comment));

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
        reviewMongoRepo.findById(reviewId)
                .orElseThrow(()-> new ItemNotFoundException("Review not found, No comment list to be displayed"));
        return ResponseEntity.ok().body(commentMongoRepo.findAllByRelatedReviewId(reviewId));
    }
}