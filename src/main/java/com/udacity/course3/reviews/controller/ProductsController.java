package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.exceptions.ItemNotFoundException;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    // TODO: Wire JPA repositories here
    private ProductRepository productRepository;
    private ValidationService validationService;

    public ProductsController(ProductRepository productRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody Product product, BindingResult result) throws URISyntaxException {

        ResponseEntity errorMap = validationService.validationService(result);
        if(errorMap!=null) return errorMap;

        return ResponseEntity.created(new URI("/products")).body(productRepository.save(product));
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("Item not found")));
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */

    @GetMapping
    public Iterable listProducts() {
        return productRepository.findAll();
    }
}