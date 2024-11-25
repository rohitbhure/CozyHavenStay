package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.ReviewRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.ReviewMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Review;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.ReviewRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public Review addReview(ReviewRequestDTO reviewRequestDTO) {
        User user = userRepository.findById(reviewRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + reviewRequestDTO.getUserId()));

        Hotel hotel = hotelRepository.findById(reviewRequestDTO.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + reviewRequestDTO.getHotelId()));

        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.setUser(user); // Ensure user is explicitly set
        review.setHotel(hotel); // Ensure hotel is explicitly set

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByHotel(Long hotelId) {
        return reviewRepository.findByHotelHotelId(hotelId);
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserUserId(userId);
    }
}