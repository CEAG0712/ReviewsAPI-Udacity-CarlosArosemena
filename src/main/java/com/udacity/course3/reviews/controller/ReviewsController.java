package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.exceptions.ItemNotFoundException;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
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
 * Spring REST controller for working with review entity.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    // TODO: Wire JPA repositories here

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private ValidationService validationService;

    public ReviewsController(ProductRepository productRepository, ReviewRepository reviewRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.validationService = validationService;
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
                                                 @Valid @RequestBody Review review, BindingResult result) {

        //Set up an enum on the DB. I was really curious about trying that constraint. it works
        //review_rating ENUM('GREAT', 'GOOD', 'AVERAGE', 'POOR', 'UNACCEPTABLE'),
        //Only takes those values, if else, throws an exception from the DB
        ResponseEntity errorMap = validationService.validationService(result);
        if(errorMap!=null) return errorMap;

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ItemNotFoundException("Product not found"));
        review.setProduct(product);

        return ResponseEntity.ok(reviewRepository.save(review));
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<List> listReviewsForProduct(@PathVariable("productId") Long productId) {
        productRepository.findById(productId)
                .orElseThrow(()-> new ItemNotFoundException("Product not found, No review list to be displayed"));
        return ResponseEntity.ok().body(reviewRepository.findAllByProductId(productId));
    }
}