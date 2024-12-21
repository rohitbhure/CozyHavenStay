package com.hexaware.CozyHavenStay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hexaware.CozyHavenStay.model.Review;
import com.hexaware.CozyHavenStay.repository.ReviewRepository;
import com.hexaware.CozyHavenStay.service.ReviewServiceImpl;

@SpringBootTest
class ReviewServiceImplTest {

    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewServiceImpl reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        // Creating a mock review object for the tests
        review = new Review();
        review.setId(1L);
        review.setComment("Great stay!");
        review.setHotel(null); // Assume hotel is another entity
        review.setUser(null);  // Assume user is another entity
        review.setRating(5);
    }

    @Test
    void testSavereview() {
        when(reviewRepository.save(ArgumentMatchers.any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.savereview(review);

        assertNotNull(savedReview);
        assertEquals("Great stay!", savedReview.getComment());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testGetreviewbyid() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review foundReview = reviewService.getreviewbyid(1L);

        assertNotNull(foundReview);
        assertEquals(1L, foundReview.getId());
        verify(reviewRepository, times(1)).findById(1L);
    }
    

    @Test
    void testDeletereview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        
        String result = reviewService.deletereview(1L);

        assertEquals("Deleted", result);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void testDeletereviewNotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        String result = reviewService.deletereview(1L);

        assertEquals("Not Found", result);
        verify(reviewRepository, times(0)).delete(any());
    }

    @Test
    void testGetallreviews() {
        List<Review> reviews = Arrays.asList(review);

        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getallreviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetreviewbyhotelid() {
        List<Review> reviews = Arrays.asList(review);

        when(reviewRepository.findByHotel_Id(1L)).thenReturn(reviews);

        List<Review> result = reviewService.getreviewbyhotelid(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findByHotel_Id(1L);
    }

    @Test
    void testGetreviewbyuserid() {
        List<Review> reviews = Arrays.asList(review);

        when(reviewRepository.findByUser_Id(1L)).thenReturn(reviews);

        List<Review> result = reviewService.getreviewbyuserid(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findByUser_Id(1L);
    }
}