package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void contextLoads() {
//		assertNotNull(productRepository);
//		assertNotNull(reviewRepository);
//		assertNotNull(commentRepository);
	}



	@BeforeTransaction
	public void loadUpH2_WithProduct(){
		//Load up a new product
		Product product = new Product("Online Course", "Online Course to learn xxxx");
		productRepository.save(product);


		Review reviewGREAT = new Review("Loved It","No challenges all good", "GREAT");
		reviewGREAT.setProduct(product);
		reviewRepository.save(reviewGREAT);
		Review reviewUNACCEPTABLE = new Review("Content could be better", "Not happy about this at all", "UNACCEPTABLE" );
		reviewUNACCEPTABLE.setProduct(product);
		reviewRepository.save(reviewUNACCEPTABLE);
		Review reviewPOOR = new Review("Poor mentor support", "Not happy about this at all", "POOR");
		reviewPOOR.setProduct(product);
		reviewRepository.save(reviewPOOR);


		Comment comment1 = new Comment("I agree");
		comment1.setReview(reviewPOOR);
		commentRepository.save(comment1);
		Comment comment2 =new Comment("I agree, so sad");
		comment2.setReview(reviewPOOR);
		commentRepository.save(comment2);

//		Comment comment3 = commentRepository.save(new Comment("I agree, so sad, needs to be fixed ASAP"));
//		comment3.setReview(reviewPOOR);
	}

	@Test
	public void FindProductById_Positive_Found(){

		Optional<Product> productInDB = productRepository.findById(1L);

		assertNotNull(productInDB);

		assertEquals(productInDB.get().getProductName(), "Online Course");
	}

//Using java11
	@Test
	public void FindProductById_Not_Found(){
		Optional<Product> notFound = productRepository.findById(11L);
		assertFalse(notFound.isPresent());
		//assert(notFound.isEmpty());//https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Optional.html
		//is empty is available!!
		/*
		isEmpty
public boolean isEmpty()
If a value is not present, returns true, otherwise false.
Returns:
true if a value is not present, otherwise false
Since:
11
		 */

	}



	@Test
	public void findReviewsByProduct_Positive(){
		Optional<Product> product = productRepository.findById(1L);
		assertNotNull(product);

		List<Review> reviews = reviewRepository.findAllByProductId(1L);

		assertEquals(3,reviews.size());
	}

	@Test
	public void findCommentsByReview_Positive(){
		Optional<Review> review = reviewRepository.findById(3L);
		assertNotNull(review);

		List<Comment> comments = commentRepository.findAllByReviewId(3L);

		assertEquals(2,comments.size());
	}



}