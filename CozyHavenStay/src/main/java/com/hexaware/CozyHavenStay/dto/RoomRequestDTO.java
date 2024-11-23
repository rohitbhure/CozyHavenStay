package com.hexaware.CozyHavenStay.dto;

import jakarta.validation.constraints.NotNull;

public class RoomRequestDTO {
	
		private String roomType;

	    @NotNull
	    private Double pricePerNight;

	    private String amenities;

	    @NotNull
	    private Boolean availabilityStatus;

		public RoomRequestDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public RoomRequestDTO(String roomType, @NotNull Double pricePerNight, String amenities,
				@NotNull Boolean availabilityStatus) {
			super();
			this.roomType = roomType;
			this.pricePerNight = pricePerNight;
			this.amenities = amenities;
			this.availabilityStatus = availabilityStatus;
		}

		public String getRoomType() {
			return roomType;
		}

		public void setRoomType(String roomType) {
			this.roomType = roomType;
		}

		public Double getPricePerNight() {
			return pricePerNight;
		}

		public void setPricePerNight(Double pricePerNight) {
			this.pricePerNight = pricePerNight;
		}

		public String getAmenities() {
			return amenities;
		}

		public void setAmenities(String amenities) {
			this.amenities = amenities;
		}

		public Boolean getAvailabilityStatus() {
			return availabilityStatus;
		}

		public void setAvailabilityStatus(Boolean availabilityStatus) {
			this.availabilityStatus = availabilityStatus;
		}
	    
}
