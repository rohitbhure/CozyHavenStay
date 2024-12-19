package com.hexaware.CozyHavenStay.dto;

import java.util.ArrayList;
import java.util.List;

import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.model.Hotel;

import jakarta.persistence.ElementCollection;

public class RoomDTO {
    private Long id;
    private String roomType; 
    private int maxOccupancy; 
    private double baseFare;
    private boolean isAC;
    
    // Added roomSize field
    private double roomSize; // New field

    @ElementCollection
    private List<String> features;
    
    private Hotel hotel;
    
    private List<Booking> bookings = new ArrayList<>();

    // Constructor updated with roomSize
    public RoomDTO(Long id, String roomType, int maxOccupancy, double baseFare, boolean isAC, List<String> features,
                   Hotel hotel, List<Booking> bookings, double roomSize) {
        super();
        this.id = id;
        this.roomType = roomType;
        this.maxOccupancy = maxOccupancy;
        this.baseFare = baseFare;
        this.isAC = isAC;
        this.features = features;
        this.hotel = hotel;
        this.bookings = bookings;
        this.roomSize = roomSize; // Initialize roomSize
    }

    // Getter and Setter for roomSize
    public double getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(double roomSize) {
        this.roomSize = roomSize;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public boolean isAC() {
        return isAC;
    }

    public void setAC(boolean isAC) {
        this.isAC = isAC;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // toString method updated with roomSize
    @Override
    public String toString() {
        return "RoomDTO [id=" + id + ", roomType=" + roomType + ", maxOccupancy=" + maxOccupancy + ", baseFare=" 
                + baseFare + ", isAC=" + isAC + ", features=" + features + ", hotelId=" + (hotel != null ? hotel.getId() : "N/A") 
                + ", bookingsCount=" + (bookings != null ? bookings.size() : 0) + ", roomSize=" + roomSize + "]"; // Added roomSize to string
    }

    public RoomDTO() {
        
    }
}
