package com.udacity.course3.reviews.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product Name cannot be null")
    @Size(min=2, message = "Your product name needs to contain more than two characters")
    private String productName;

    private String productDescription;

    /*
    A Product in real life can have 1 or more reviews. A review can only belong to one product.

     */
    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList();

    //Constructor w/o the ID & Review List


    public Product(@NotBlank(message = "Product Name cannot be null") @Size(min = 2, message = "Your product name needs to contain more than two characters") String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public Product(Long id, @NotBlank(message = "Product Name cannot be null") @Size(min = 2, message = "Your product name needs to contain more than two characters") String productName, String productDescription) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
    }


}
