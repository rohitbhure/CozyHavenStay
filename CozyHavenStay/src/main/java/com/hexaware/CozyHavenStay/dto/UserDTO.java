package com.hexaware.CozyHavenStay.dto;

import java.util.Date;

import com.hexaware.CozyHavenStay.Enum.Roles;

public class UserDTO {
    private Long id;
    
    private String name;
    private String username;
    
    private String email;
    private String password;
    
    private Roles role; // "USER", "OWNER", or "ADMIN"
    
    // New fields for UserDTO
    private String gender; // Gender (e.g., "Male", "Female", "Other")
    private Date dateOfBirth; // Date of Birth
    private String profilePictureUrl; // URL of profile picture (or you can use byte[] if you want image data)

    // Constructor with new fields
    public UserDTO(Long id, String name, String username, String email, String password, Roles role, 
                   String gender, Date dateOfBirth, String profilePictureUrl) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and Setters for the new fields
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    // Getters and Setters for existing fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password=" 
                + password + ", role=" + role + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth 
                + ", profilePictureUrl=" + profilePictureUrl + "]";
    }
}
