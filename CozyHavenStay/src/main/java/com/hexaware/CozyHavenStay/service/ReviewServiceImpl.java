package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.ReviewRequestDTO;
import com.hexaware.CozyHavenStay.mapper.ReviewMapper;
import com.hexaware.CozyHavenStay.model.Review;
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
        Review review = reviewMapper.toEntity(reviewRequestDTO);
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