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
    public void updateEntity(User user, UserRequestDTO dto) {
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        // Avoid updating password directly unless explicitly requested
    }
}