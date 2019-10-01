package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.ReviewMDBDocument;
import com.udacity.course3.reviews.repository.ReviewMongoRepo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
public class ReviewApplicationTests {

    @Autowired
    private MongodExecutable mongodExecutable;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReviewMongoRepo reviewMongoRepo;

    @BeforeEach
    public void loadDBS() throws IOException {

        Product product = new Product("Online Course", "Online Course to learn xxxx");
        product.setId(1L);

        ReviewMDBDocument reviewGREAT = new ReviewMDBDocument("Loved It","No challenges all good", "GREAT");
        reviewGREAT.setRelatedProductId(product.getId());
        reviewGREAT.setId(1L);
//        mongoDBSaveService.saveMongoReview(reviewGREAT);
        reviewMongoRepo.save(reviewGREAT);
        // mongoTemplate.save(reviewGREAT,"reviews");
        ReviewMDBDocument reviewUNACCEPTABLE = new ReviewMDBDocument("Content could be better", "Not happy about this at all", "UNACCEPTABLE" );
        reviewUNACCEPTABLE.setRelatedProductId(product.getId());
        reviewGREAT.setId(2L);
        //mongoTemplate.save(reviewUNACCEPTABLE,"reviews");

//        mongoDBSaveService.saveMongoReview(reviewUNACCEPTABLE);
        reviewMongoRepo.save(reviewUNACCEPTABLE);

        ReviewMDBDocument reviewPOOR = new ReviewMDBDocument("Poor mentor support", "Not happy about this at all", "POOR");
        reviewPOOR.setRelatedProductId(product.getId());
        reviewGREAT.setId(3L);
        //mongoTemplate.save(reviewPOOR,"reviews");

        reviewMongoRepo.save(reviewPOOR);

//        mongoDBSaveService.saveMongoReview(reviewPOOR);
//
//        CommentMDBDocument comment1 = new CommentMDBDocument("I agree");
//        comment1.setRelatedReviewId(reviewPOOR.getId());
//        mongoDBSaveService.saveMongoComment(comment1);
//        CommentMDBDocument comment2 =new CommentMDBDocument("I agree, so sad");
//        comment2.setRelatedReviewId(reviewPOOR.getId());
//        mongoDBSaveService.saveMongoComment(comment2);
    }

    @Test
    public void findReviewsByProduct_Positive(){
//        Optional<Product> product = productRepository.findById(1L);
//        assertNotNull(product);

        List<ReviewMDBDocument> reviews = reviewMongoRepo.findAllByRelatedProductId(1L);

        assertEquals(3,reviews.size());
    }
}
