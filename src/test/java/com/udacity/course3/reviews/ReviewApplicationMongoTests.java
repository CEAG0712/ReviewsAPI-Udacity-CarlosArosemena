package com.udacity.course3.reviews;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.udacity.course3.reviews.model.CommentMDBDocument;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.ReviewMDBDocument;
import com.udacity.course3.reviews.repository.CommentMongoRepo;
import com.udacity.course3.reviews.repository.ReviewMongoRepo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ReviewApplicationMongoTests {


    @Autowired
    private CommentMongoRepo commentMongoRepo;
    @Autowired
    private ReviewMongoRepo reviewMongoRepo;

    @Before
    public void loadDBS(){


        Product product = new Product("Online Course", "Online Course to learn xxxx");
        product.setId(1L);

        ReviewMDBDocument reviewGREAT = new ReviewMDBDocument("Loved It","No challenges all good", "GREAT");
        reviewGREAT.setRelatedProductId(product.getId());
        reviewGREAT.setId(1L);
        reviewMongoRepo.insert(reviewGREAT);
        ReviewMDBDocument reviewUNACCEPTABLE = new ReviewMDBDocument("Content could be better", "Not happy about this at all", "UNACCEPTABLE" );
        reviewUNACCEPTABLE.setRelatedProductId(product.getId());
        reviewGREAT.setId(2L);

        reviewMongoRepo.insert(reviewUNACCEPTABLE);

        ReviewMDBDocument reviewPOOR = new ReviewMDBDocument("Poor mentor support", "Not happy about this at all", "POOR");
        reviewPOOR.setRelatedProductId(product.getId());
        reviewGREAT.setId(3L);
        //mongoTemplate.save(reviewPOOR,"reviews");

        reviewMongoRepo.insert(reviewPOOR);



        CommentMDBDocument comment1 = new CommentMDBDocument("I agree");
        comment1.setRelatedReviewId(reviewPOOR.getId());
        commentMongoRepo.save(comment1);
        CommentMDBDocument comment2 =new CommentMDBDocument("I agree, so sad");
        comment2.setRelatedReviewId(reviewPOOR.getId());
        commentMongoRepo.save(comment2);
    }

    @Test
    public void findReviewsByProduct_Positive(){

        List<ReviewMDBDocument> reviews = reviewMongoRepo.findAllByRelatedProductId(1L);

        assertEquals(3,reviews.size());
    }

    @Test
    public void findCommentsByReview_Positive(){
        Optional<ReviewMDBDocument> review = reviewMongoRepo.findById(3L);
        assertNotNull(review);

        List<CommentMDBDocument> comments = commentMongoRepo.findAllByRelatedReviewId(3L);

        assertEquals(2,comments.size());
    }
}
