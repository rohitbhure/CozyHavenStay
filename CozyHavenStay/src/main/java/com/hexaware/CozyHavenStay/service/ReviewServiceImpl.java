package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.model.Review;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.ReviewRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Review addReview(Review review) {
        // Validate user and hotel
        userRepository.findById(review.getUser().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        hotelRepository.findById(review.getHotel().getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByHotel(Long hotelId) {
        return reviewRepository.findByHotelHotelId(hotelId);
    }
}