package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.UserRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.UserMapper;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(UserRequestDTO userRequestDTO) {
        // Convert DTO to entity
        User user = userMapper.toEntity(userRequestDTO);
        // Save entity
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        // Fetch user or throw exception
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        // Fetch all users
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long userId, UserRequestDTO userRequestDTO) {
        // Fetch existing user
        User existingUser = getUserById(userId);
        // Map updated fields from DTO to existing entity
        userMapper.updateEntity(existingUser, userRequestDTO);
        // Save updated entity
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        // Delete user
        userRepository.deleteById(userId);
    }
}