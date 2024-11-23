package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.dto.ReviewRequestDTO;
import com.hexaware.CozyHavenStay.model.Review;

public interface ReviewService {
    Review addReview(ReviewRequestDTO reviewRequestDTO);
    List<Review> getReviewsByHotel(Long hotelId);
    List<Review> getReviewsByUser(Long userId);
}