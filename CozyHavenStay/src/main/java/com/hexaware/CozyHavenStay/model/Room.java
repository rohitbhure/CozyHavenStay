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
}