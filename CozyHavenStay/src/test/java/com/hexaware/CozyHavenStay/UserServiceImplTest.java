package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.CozyHavenStay.Enum.Roles;
import com.hexaware.CozyHavenStay.model.HotelOwner;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.HotelOwnerRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;
import com.hexaware.CozyHavenStay.service.UserServiceImpl;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HotelOwnerRepository hotelOwnerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserAsHotelOwner() {
        User user = new User();
        user.setRole(Roles.HOTEL_OWNER);

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals(user, result);
        verify(hotelOwnerRepository, times(1)).save(any(HotelOwner.class));
    }

    @Test
    void testRegisterUserAsRegularUser() {
        User user = new User();
        user.setRole(Roles.USER);

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals(user, result);
        verify(hotelOwnerRepository, never()).save(any(HotelOwner.class));
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");
        existingUser.setUsername("oldUsername");

        User userDetails = new User();
        userDetails.setName("New Name");
        userDetails.setEmail("new@example.com");
        userDetails.setUsername("newUsername");
        userDetails.setPassword("newPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(1L, userDetails);

        assertEquals("New Name", updatedUser.getName());
        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("encodedPassword", updatedUser.getPassword());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1L);

        assertEquals("Deleted", result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        String result = userService.deleteUser(1L);

        assertEquals("Not Found", result);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testShowAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.showall();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void testGetUsersByRole() {
        User user1 = new User();
        user1.setRole(Roles.HOTEL_OWNER);

        List<User> users = Arrays.asList(user1);
        when(userRepository.findByRole(Roles.HOTEL_OWNER)).thenReturn(users);

        List<User> result = userService.getUsersByRole(Roles.HOTEL_OWNER);

        assertEquals(1, result.size());
        assertEquals(user1, result.get(0));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("testUsername");

        assertEquals(user, result);
    }
}
