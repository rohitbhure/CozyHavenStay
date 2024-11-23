package com.hexaware.CozyHavenStay.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long hotelId;

    @NotNull
    @Min(1)
    @Max(5)
    private Double rating;

    private String comments;

	public ReviewRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewRequestDTO(@NotNull Long userId, @NotNull Long hotelId, @NotNull @Min(1) @Max(5) Double rating,
			String comments) {
		super();
		this.userId = userId;
		this.hotelId = hotelId;
		this.rating = rating;
		this.comments = comments;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ReviewRequestDTO [userId=" + userId + ", hotelId=" + hotelId + ", rating=" + rating + ", comments="
				+ comments + "]";
	}

}
