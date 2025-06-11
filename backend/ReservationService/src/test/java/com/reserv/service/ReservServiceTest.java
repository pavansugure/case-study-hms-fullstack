package com.reserv.service;

import com.reserv.client.GuestServiceClient;
import com.reserv.client.RoomServiceClient;
import com.reserv.model.Guest;
import com.reserv.model.Reservation;
import com.reserv.model.Room;
import com.reserv.repo.ReservRepository;
import com.reserv.service.impl.ReservServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservServiceTest {

    @Mock
    private ReservRepository reservRepository;

    @Mock
    private RoomServiceClient roomServiceClient;

    @Mock
    private GuestServiceClient guestServiceClient;

    @InjectMocks
    private ReservServiceImpl reservService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testMakeReservationSuccessfully() {
        // Arrange
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(3); // 3 nights
        Reservation reservation = new Reservation("RES123", 1L, 101L, 2, 2,
                checkIn, checkOut, null, 0, null);

        Guest guest = new Guest("MEM123", 9876543210L, "Company A", "John Doe", 
                                "john.doe@example.com", "Male", "123 Street");
        when(guestServiceClient.getGuestById(1L)).thenReturn(guest);

        when(roomServiceClient.isRoomAvailable(101L, checkIn.toString(), checkOut.toString())).thenReturn(true);

        Room room = new Room();
        room.setId(101L);
        room.setType("Deluxe");
        room.setAvailable(true);
        room.setPrice(1000.0);
        room.setCheckInDate(checkIn);
        room.setCheckOutDate(checkOut);
        when(roomServiceClient.getRoomById(101L)).thenReturn(room);

        Reservation expectedReservation = new Reservation("RES123", 1L, 101L, 2, 2,
                checkIn, checkOut, "CONFIRMED", 3, 3000.0);

        when(reservRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

        // Act
        Reservation createdReservation = reservService.makeReservation(reservation);

        // Assert
        assertNotNull(createdReservation);
        assertEquals("CONFIRMED", createdReservation.getStatus());
        assertEquals(3, createdReservation.getNumberOfNights());
        assertEquals(3000.0, createdReservation.getAmount());
        verify(guestServiceClient, times(1)).getGuestById(1L);
        verify(roomServiceClient, times(1)).isRoomAvailable(101L, checkIn.toString(), checkOut.toString());
        verify(roomServiceClient, times(1)).getRoomById(101L);
        verify(reservRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testMakeReservationWithInvalidDates() {
        // Arrange
        Reservation reservation = new Reservation("RES124", 2L, 102L, 1, 2,
                LocalDate.now().plusDays(3), LocalDate.now(), null, 0, null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class,
                () -> reservService.makeReservation(reservation));
        assertEquals("Check-out date must be after the check-in date.", exception.getMessage());
        verify(guestServiceClient, never()).getGuestById(anyLong());
        verify(roomServiceClient, never()).isRoomAvailable(anyLong(), anyString(), anyString());
        verify(reservRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testMakeReservationWithGuestNotFound() {
        // Arrange
        Reservation reservation = new Reservation("RES125", 3L, 103L, 0, 2,
                LocalDate.now(), LocalDate.now().plusDays(2), null, 0, null);

        when(guestServiceClient.getGuestById(3L)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class,
                () -> reservService.makeReservation(reservation));
        assertEquals("Guest not found!", exception.getMessage());
        verify(guestServiceClient, times(1)).getGuestById(3L);
        verify(roomServiceClient, never()).isRoomAvailable(anyLong(), anyString(), anyString());
        verify(reservRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testMakeReservationWithRoomUnavailable() {
        // Arrange
        Reservation reservation = new Reservation("RES126", 4L, 104L, 0, 3,
                LocalDate.now(), LocalDate.now().plusDays(3), null, 0, null);

        Guest guest = new Guest("MEM126", 9876543210L, "Company B", "Jane Doe", 
                                "jane.doe@example.com", "Female", "456 Avenue");
        when(guestServiceClient.getGuestById(4L)).thenReturn(guest);
        when(roomServiceClient.isRoomAvailable(104L,
                reservation.getCheckInDate().toString(),
                reservation.getCheckOutDate().toString()))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class,
                () -> reservService.makeReservation(reservation));
        assertEquals("Room not available!", exception.getMessage());
        verify(guestServiceClient, times(1)).getGuestById(4L);
        verify(roomServiceClient, times(1))
                .isRoomAvailable(104L,
                        reservation.getCheckInDate().toString(),
                        reservation.getCheckOutDate().toString());
        verify(reservRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testGetReservationByCodeSuccessfully() {
        // Arrange
        Reservation reservation = new Reservation("RES127", 5L, 105L, 2, 2,
                LocalDate.now(), LocalDate.now().plusDays(5), "CONFIRMED", 5, 5000.0);

        when(reservRepository.findById("RES127")).thenReturn(Optional.of(reservation));

        // Act
        Reservation fetchedReservation = reservService.getReservationByCode("RES127");

        // Assert
        assertNotNull(fetchedReservation);
        assertEquals("CONFIRMED", fetchedReservation.getStatus());
        assertEquals(5000.0, fetchedReservation.getAmount());
        verify(reservRepository, times(1)).findById("RES127");
    }

    @Test
    void testGetReservationByCodeNotFound() {
        // Arrange
        when(reservRepository.findById("RES128")).thenReturn(Optional.empty());

        // Act
        Reservation fetchedReservation = reservService.getReservationByCode("RES128");

        // Assert
        assertNull(fetchedReservation);
        verify(reservRepository, times(1)).findById("RES128");
    }
}
