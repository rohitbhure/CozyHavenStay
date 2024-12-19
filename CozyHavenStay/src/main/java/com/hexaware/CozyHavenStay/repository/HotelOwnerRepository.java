package com.hexaware.CozyHavenStay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.HotelOwner;

@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long>{
	HotelOwner findByUserId(Long userId);
}
