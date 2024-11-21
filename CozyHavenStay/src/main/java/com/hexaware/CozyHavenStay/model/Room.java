package com.hexaware.CozyHavenStay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomType;

    private Double pricePerNight;

    @Lob
    private String amenities;

    private Boolean availabilityStatus;

    @Override
	public String toString() {
		return "Room [roomId=" + roomId + ", hotel=" + hotel + ", roomType=" + roomType + ", pricePerNight="
				+ pricePerNight + ", amenities=" + amenities + ", availabilityStatus=" + availabilityStatus + "]";
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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

	public Room(Long roomId, Hotel hotel, String roomType, Double pricePerNight, String amenities,
			Boolean availabilityStatus) {
		super();
		this.roomId = roomId;
		this.hotel = hotel;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.amenities = amenities;
		this.availabilityStatus = availabilityStatus;
	}

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}