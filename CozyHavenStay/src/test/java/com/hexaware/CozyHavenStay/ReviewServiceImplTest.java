package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.dto.ReviewRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.ReviewMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Review;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.ReviewRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;
import com.hexaware.CozyHavenStay.service.ReviewServiceImpl;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewRequestDTO reviewRequestDTO;
    private User user;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock User
        user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        // Mock Hotel
        hotel = new Hotel();
        hotel.setHotelId(101L);
        hotel.setName("Luxury Stay");

        // Mock Review
        review = new Review();
        review.setReviewId(1001L);
        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(4.5);
        review.setComments("Amazing stay!");
        review.setCreatedAt(LocalDateTime.now());

        // Mock ReviewRequestDTO
        reviewRequestDTO = new ReviewRequestDTO();
        reviewRequestDTO.setUserId(1L);
        reviewRequestDTO.setHotelId(101L);
        reviewRequestDTO.setRating(4.5);
        reviewRequestDTO.setComments("Amazing stay!");
    }

    @Test
    void testAddReview() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(101L)).thenReturn(Optional.of(hotel));
        when(reviewMapper.toEntity(reviewRequestDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);

        Review addedReview = reviewService.addReview(reviewRequestDTO);

        assertNotNull(addedReview);
        assertEquals(1001L, addedReview.getReviewId());
        assertEquals(4.5, addedReview.getRating());
        assertEquals("Amazing stay!", addedReview.getComments());
        verify(reviewMapper, times(1)).toEntity(reviewRequestDTO);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testGetReviewsByHotel() {
        List<Review> reviews = Arrays.asList(review, new Review());
        when(reviewRepository.findByHotelHotelId(101L)).thenReturn(reviews);

        List<Review> fetchedReviews = reviewService.getReviewsByHotel(101L);

        assertNotNull(fetchedReviews);
        assertEquals(2, fetchedReviews.size());
        verify(reviewRepository, times(1)).findByHotelHotelId(101L);
    }

    @Test
    void testGetReviewsByUser() {
        List<Review> reviews = Arrays.asList(review, new Review());
        when(reviewRepository.findByUserUserId(1L)).thenReturn(reviews);

        List<Review> fetchedReviews = reviewService.getReviewsByUser(1L);

        assertNotNull(fetchedReviews);
        assertEquals(2, fetchedReviews.size());
        verify(reviewRepository, times(1)).findByUserUserId(1L);
    }

    @Test
    void testAddReviewUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.addReview(reviewRequestDTO);
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(hotelRepository, reviewMapper, reviewRepository);
    }
    @Test
    void testAddReviewHotelNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(101L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.addReview(reviewRequestDTO);
        });

        assertEquals("Hotel not found with ID: 101", exception.getMessage());
        verify(hotelRepository, times(1)).findById(101L);
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(reviewMapper, reviewRepository);
    }

}
