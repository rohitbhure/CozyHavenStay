package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.model.HotelOwner;
import com.hexaware.CozyHavenStay.repository.HotelOwnerRepository;
import com.hexaware.CozyHavenStay.service.HotelOwnerService;

class HotelOwnerServiceTest {

    @InjectMocks
    private HotelOwnerService hotelOwnerService;

    @Mock
    private HotelOwnerRepository hotelOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHotelOwnerByUserId() {
        Long userId = 1L;
        HotelOwner hotelOwner = new HotelOwner();
        hotelOwner.setOwnerId(1L);
        hotelOwner.setOwnerId(userId);

        when(hotelOwnerRepository.findByUserId(userId)).thenReturn(hotelOwner);

        HotelOwner result = hotelOwnerService.getHotelOwnerByUserId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getOwnerId());
        verify(hotelOwnerRepository, times(1)).findByUserId(userId);
    }
}
