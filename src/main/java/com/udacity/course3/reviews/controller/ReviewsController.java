package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.exceptions.ItemNotFoundException;
import com.udacity.course3.reviews.model.Product;
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
import java.util.stream.Collectors;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    // TODO: Wire JPA repositories here

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private ValidationService validationService;
    private ReviewMongoRepo reviewMongoRepo;
    private MongoDBSaveService mongoDBSaveService;
    private CommentMongoRepo commentMongoRepo;

    public ReviewsController(ProductRepository productRepository, ReviewRepository reviewRepository, ValidationService validationService, ReviewMongoRepo reviewMongoRepo, MongoDBSaveService mongoDBSaveService, CommentMongoRepo commentMongoRepo) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.validationService = validationService;
        this.reviewMongoRepo = reviewMongoRepo;
        this.mongoDBSaveService = mongoDBSaveService;
        this.commentMongoRepo = commentMongoRepo;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @PostMapping("/products/{productId}")
    public ResponseEntity createReviewForProduct(@PathVariable("productId") Long productId,
                                                 @Valid @RequestBody ReviewMDBDocument review, BindingResult result) {


        ResponseEntity errorMap = validationService.validationService(result);
        if(errorMap!=null) return errorMap;

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ItemNotFoundException("Product not found"));
        review.setRelatedProductId(product.getId());

        return ResponseEntity.ok(mongoDBSaveService.saveMongoReview(review));
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity listReviewsForProduct(@PathVariable("productId") Long productId) {
        productRepository.findById(productId)
                .orElseThrow(()-> new ItemNotFoundException("Product not found, No review list to be displayed"));
        return ResponseEntity.ok().body(
                reviewMongoRepo.findAllByRelatedProductId(productId).stream().map(
                        reviewMDBDocument ->
                        {
                            reviewMDBDocument.setCommentList(commentMongoRepo.findAllByRelatedReviewId(reviewMDBDocument.getId()));
                            return reviewMDBDocument;
                        }
                ).collect(Collectors.toList())

        );
    }
}