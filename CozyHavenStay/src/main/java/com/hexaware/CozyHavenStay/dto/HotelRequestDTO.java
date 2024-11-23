package com.hexaware.CozyHavenStay.dto;

import jakarta.validation.constraints.NotBlank;

public class HotelRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private String description;

    private Double rating;

	public HotelRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HotelRequestDTO(@NotBlank String name, @NotBlank String location, String description, Double rating) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "HotelRequestDTO [name=" + name + ", location=" + location + ", description=" + description + ", rating="
				+ rating + "]";
	}

    
}
