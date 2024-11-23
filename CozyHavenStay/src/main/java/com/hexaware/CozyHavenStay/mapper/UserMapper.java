package com.hexaware.CozyHavenStay.mapper;

import org.springframework.stereotype.Component;

import com.hexaware.CozyHavenStay.dto.UserRequestDTO;
import com.hexaware.CozyHavenStay.model.User;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Ensure password is hashed in the service layer
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}