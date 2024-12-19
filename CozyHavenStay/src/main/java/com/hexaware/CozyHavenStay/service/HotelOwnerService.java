package com.hexaware.CozyHavenStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.model.HotelOwner;
import com.hexaware.CozyHavenStay.repository.HotelOwnerRepository;

@Service
public class HotelOwnerService {
	
	@Autowired
    private HotelOwnerRepository hotelOwnerRepository;

    public HotelOwner getHotelOwnerByUserId(Long userId) {
        return hotelOwnerRepository.findByUserId(userId);
    }

}
