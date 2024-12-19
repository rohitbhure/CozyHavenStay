package com.hexaware.CozyHavenStay.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.Enum.Roles;
import com.hexaware.CozyHavenStay.model.HotelOwner;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.HotelOwnerRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;

@Service
public class UserServiceImpl {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;  
    
    @Autowired
    private HotelOwnerRepository hotelOwnerRepository;  

    public User registerUser(User user) {
        // Encoding the password before saving
        user.setPassword(encodePassword(user.getPassword()));
        
        // Saving the user and checking for role
        User user1 = userRepository.save(user);
        
        if (user1.getRole() == Roles.HOTEL_OWNER) {
            HotelOwner hotelOwner = new HotelOwner();
            hotelOwner.setUser(user1);
            hotelOwnerRepository.save(hotelOwner);
        }
        return user1;
    }

    // Find a user by their ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update the user details
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setUsername(userDetails.getUsername());

        // Update the optional fields (newly added fields)
        if (userDetails.getGender() != null) {
            existingUser.setGender(userDetails.getGender());
        }
        if (userDetails.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(userDetails.getDateOfBirth());
        }
        if (userDetails.getProfilePicture() != null) {
            existingUser.setProfilePicture(userDetails.getProfilePicture());
        }

        // Update password only if not null
        if (userDetails.getPassword() != null) {
            existingUser.setPassword(encodePassword(userDetails.getPassword()));
        }

        // Save the updated user
        return userRepository.save(existingUser);
    }

    public String deleteUser(Long id) {
        User u = userRepository.findById(id).orElse(null);
        if (u != null) {
            userRepository.deleteById(id);
            return "Deleted";
        } else {
            return "Not Found";
        }
    }

    public List<User> showall() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(Roles role) {
        return userRepository.findByRole(role);
    }

    public String encodePassword(String password) {
        // Encrypt the password using BCryptPasswordEncoder
        return passwordEncoder.encode(password);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User save(User user) {
        // Encrypting the password before saving the user
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }
}
