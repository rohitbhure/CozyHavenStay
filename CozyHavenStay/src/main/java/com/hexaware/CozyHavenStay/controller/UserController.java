package com.hexaware.CozyHavenStay.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.CozyHavenStay.Enum.Roles;
import com.hexaware.CozyHavenStay.dto.UserDTO;
import com.hexaware.CozyHavenStay.exception.UserNotFoundException;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.service.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper mp;
    
    @GetMapping("/allusers")
    public ResponseEntity<List<UserDTO>> getallusers(){
    	List<User> user =userService.showall();
    	List<UserDTO> users=new ArrayList<>();
    	for(User u : user)
    	{
    		UserDTO x = mp.map(u, UserDTO.class);
    		users.add(x);
    	}
    	if(user.isEmpty()) {
    		return new ResponseEntity<List<UserDTO>>(users,HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<UserDTO>>(users,HttpStatus.OK);
    }

    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO user) {
        // Map UserDTO to User entity
        User user1 = mp.map(user, User.class);

        // Set profile picture if provided
        if (user.getProfilePicture() != null && user.getProfilePicture().length > 0) {
            user1.setProfilePicture(user.getProfilePicture());
        }

        // Encode the password
        user1.setPassword(userService.encodePassword(user1.getPassword()));

        // Save the user
        User user2 = userService.registerUser(user1);

        // Return the response
        return new ResponseEntity<>(user2, HttpStatus.CREATED);
    }


    @GetMapping("/getuser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = mp.map(user.get(), UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
    
    @GetMapping("/getuserbyusername/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user!=null) {
            UserDTO userDTO = mp.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDetails) {
        User userEntity = mp.map(userDetails, User.class);
        User updatedUser = userService.updateUser(id, userEntity);
        
        UserDTO userDTO = mp.map(updatedUser, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String str= userService.deleteUser(id);
        if(str.equals("Deleted"))
        return new ResponseEntity<>(str,HttpStatus.OK);
        else {
			return new ResponseEntity<>(str,HttpStatus.NOT_FOUND);
		}
    }
    
    @GetMapping("allUsersWithRoleUser")
    public ResponseEntity<List<UserDTO>> getAllUsersWithRoleUser() {
        List<User> user = userService.getUsersByRole(Roles.USER);
        List<UserDTO> users=new ArrayList<>();
        for(User u : user)
    	{
    		UserDTO x = mp.map(u, UserDTO.class);
    		users.add(x);
    	}
        if (users.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("allUsersWithRoleHotelOwner")
    public ResponseEntity<List<UserDTO>> getAllUsersWithRoleHotelOwner() {
        List<User> user = userService.getUsersByRole(Roles.HOTEL_OWNER);
        List<UserDTO> users=new ArrayList<>();
        for(User u : user)
    	{
    		UserDTO x = mp.map(u, UserDTO.class);
    		users.add(x);
    	}
        if (users.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
   


    @PostMapping("/uploadProfileImage/{id}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long id, 
                                                     @RequestParam("file") MultipartFile file) {
        try {
            User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            
            // Convert MultipartFile to byte[] and update the user's profile image
            byte[] profileImageBytes = file.getBytes();
            user.setProfilePicture(profileImageBytes);
            userService.save(user); // Save the updated user with the image
            
            return new ResponseEntity<>("Profile image uploaded successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProfileImage/{id}")
   
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        try {
            User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            byte[] profileImage = user.getProfilePicture();

            if (profileImage != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG); // Change to IMAGE_PNG if the image is PNG
                return new ResponseEntity<>(profileImage, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No image found
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
