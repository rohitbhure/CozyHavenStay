package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.model.Review;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByHotel(Long hotelId);
}