package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.model.Hotel;

public interface HotelService {
    Hotel addHotel(Hotel hotel);
    Hotel getHotelById(Long hotelId);
    List<Hotel> getAllHotels();
    Hotel updateHotel(Long hotelId, Hotel hotel);
    void deleteHotel(Long hotelId);
}