package com.guest.service;

import com.guest.model.Guest;
import com.guest.repo.GuestRepository;
import com.guest.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddGuestSuccessfully() {
        Guest guest = new Guest(1L, 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");

        when(guestRepository.existsById(1L)).thenReturn(false);
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        Guest savedGuest = guestService.addGuest(guest);

        assertNotNull(savedGuest);
        assertEquals("John Doe", savedGuest.getGuestName());
        verify(guestRepository, times(1)).save(guest);
    }

    @Test
    void testAddGuestWithExistingMemberCode() {
        Guest guest = new Guest(1L, 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");

        when(guestRepository.existsById(1L)).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> guestService.addGuest(guest));

        assertEquals("Guest with MemberCode already exists.", exception.getMessage());
        verify(guestRepository, never()).save(guest);
    }

    @Test
    void testUpdateGuestSuccessfully() {
        Guest guest = new Guest(1L, 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");

        when(guestRepository.existsById(1L)).thenReturn(true);
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        Guest updatedGuest = guestService.updateGuest(guest);

        assertNotNull(updatedGuest);
        assertEquals("John Doe", updatedGuest.getGuestName());
        verify(guestRepository, times(1)).save(guest);
    }

    @Test
    void testUpdateGuestNotFound() {
        Guest guest = new Guest(1L, 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");

        when(guestRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> guestService.updateGuest(guest));

        assertEquals("Guest not found.", exception.getMessage());
        verify(guestRepository, never()).save(guest);
    }

    @Test
    void testDeleteGuestSuccessfully() {
        when(guestRepository.existsById(1L)).thenReturn(true);
        doNothing().when(guestRepository).deleteById(1L);

        guestService.deleteGuest(1L);

        verify(guestRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteGuestNotFound() {
        when(guestRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> guestService.deleteGuest(1L));

        assertEquals("Guest not found.", exception.getMessage());
        verify(guestRepository, never()).deleteById(1L);
    }

    @Test
    void testGetGuestByIdSuccessfully() {
        Guest guest = new Guest(1L, 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");

        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        Guest fetchedGuest = guestService.getGuestById(1L);

        assertNotNull(fetchedGuest);
        assertEquals("John Doe", fetchedGuest.getGuestName());
        verify(guestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGuestByIdNotFound() {
        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        Guest fetchedGuest = guestService.getGuestById(1L);

        assertNull(fetchedGuest);
        verify(guestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllGuests() {
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest(1L, 9876543210L, "Company A", "John Doe", 
                             "john.doe@example.com", "Male", "123 Street"));
        guests.add(new Guest(2L, 9123456789L, "Company B", "Jane Smith", 
                             "jane.smith@example.com", "Female", "456 Avenue"));

        when(guestRepository.findAll()).thenReturn(guests);

        List<Guest> fetchedGuests = guestService.getAllGuests();

        assertNotNull(fetchedGuests);
        assertEquals(2, fetchedGuests.size());
        verify(guestRepository, times(1)).findAll();
    }
}
