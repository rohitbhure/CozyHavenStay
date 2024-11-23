package com.hexaware.CozyHavenStay.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long hotelId;

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

	public BookingRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingRequestDTO(@NotNull Long userId, @NotNull Long hotelId, @NotNull Long roomId,
			@NotNull LocalDate checkInDate, @NotNull LocalDate checkOutDate) {
		super();
		this.userId = userId;
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
    
    
}
