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

import com.hexaware.CozyHavenStay.dto.HotelRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.HotelMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.service.HotelServiceImpl;

class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;
    private HotelRequestDTO hotelRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock objects
        hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Luxury Stay");
        hotel.setLocation("City Center");

        hotelRequestDTO = new HotelRequestDTO();
        hotelRequestDTO.setName("Luxury Stay");
        hotelRequestDTO.setLocation("City Center");
    }

    @Test
    void testAddHotel() {
        when(hotelMapper.toEntity(hotelRequestDTO)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel addedHotel = hotelService.addHotel(hotelRequestDTO);

        assertNotNull(addedHotel);
        assertEquals(hotel.getHotelId(), addedHotel.getHotelId());
        verify(hotelMapper, times(1)).toEntity(hotelRequestDTO);
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testGetHotelById_HotelExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel fetchedHotel = hotelService.getHotelById(1L);

        assertNotNull(fetchedHotel);
        assertEquals(hotel.getHotelId(), fetchedHotel.getHotelId());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void testGetHotelById_HotelDoesNotExist() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelService.getHotelById(1L));
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllHotels() {
        List<Hotel> hotels = Arrays.asList(hotel, new Hotel());
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<Hotel> fetchedHotels = hotelService.getAllHotels();

        assertNotNull(fetchedHotels);
        assertEquals(2, fetchedHotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testUpdateHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        doNothing().when(hotelMapper).updateEntity(hotel, hotelRequestDTO);
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel updatedHotel = hotelService.updateHotel(1L, hotelRequestDTO);

        assertNotNull(updatedHotel);
        assertEquals(hotel.getName(), updatedHotel.getName());
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelMapper, times(1)).updateEntity(hotel, hotelRequestDTO);
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testDeleteHotel() {
        doNothing().when(hotelRepository).deleteById(1L);

        hotelService.deleteHotel(1L);

        verify(hotelRepository, times(1)).deleteById(1L);
    }
}
