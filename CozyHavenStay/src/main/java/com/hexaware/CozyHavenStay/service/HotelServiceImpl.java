package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.HotelRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.HotelMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.repository.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public Hotel addHotel(HotelRequestDTO hotelRequestDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelRequestDTO);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel updateHotel(Long hotelId, HotelRequestDTO hotelRequestDTO) {
        Hotel existingHotel = getHotelById(hotelId);
        hotelMapper.updateEntity(existingHotel, hotelRequestDTO);
        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }
}