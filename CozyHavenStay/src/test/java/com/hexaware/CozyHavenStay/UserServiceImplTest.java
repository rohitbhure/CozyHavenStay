package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.dto.UserRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.UserMapper;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.UserRepository;
import com.hexaware.CozyHavenStay.service.UserServiceImpl;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock user and DTO objects
        user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("John Doe");
        userRequestDTO.setEmail("john.doe@example.com");
    }

    @Test
    void testCreateUser() {
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(userRequestDTO);

        assertNotNull(createdUser);
        assertEquals(user.getName(), createdUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User fetchedUser = userService.getUserById(1L);

        assertNotNull(fetchedUser);
        assertEquals(user.getName(), fetchedUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> fetchedUsers = userService.getAllUsers();

        assertNotNull(fetchedUsers);
        assertEquals(2, fetchedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateEntity(user, userRequestDTO);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(1L, userRequestDTO);

        assertNotNull(updatedUser);
        assertEquals(user.getName(), updatedUser.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).updateEntity(user, userRequestDTO);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
