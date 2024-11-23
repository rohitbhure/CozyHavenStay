package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.dto.UserRequestDTO;
import com.hexaware.CozyHavenStay.model.User;

public interface UserService {
    User createUser(UserRequestDTO userRequestDTO);
    User getUserById(Long userId);
    List<User> getAllUsers();
    User updateUser(Long userId, UserRequestDTO userRequestDTO);
    void deleteUser(Long userId);
}