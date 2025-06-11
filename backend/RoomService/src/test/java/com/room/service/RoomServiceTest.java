package com.room.service;

import com.room.entity.Room;
import com.room.repo.RoomRepository;
import com.room.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddRoomSuccessfully() {
        // Arrange
        Room room = new Room(null, "Deluxe", true, 4, 2000.0);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> {
            Room savedRoom = invocation.getArgument(0);
            savedRoom.setId(1L); // Simulate ID assignment on save
            return savedRoom;
        });

        // Act
        Room savedRoom = roomService.addRoom(room);

        // Assert
        assertNotNull(savedRoom);
        assertEquals("Deluxe", savedRoom.getType());
        assertTrue(savedRoom.getAvailable());
        assertEquals(4, savedRoom.getCapacity());
        assertEquals(2000.0, savedRoom.getPrice());
        assertEquals(1L, savedRoom.getId());
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testAddRoomWithEmptyType() {
        // Arrange
        Room room = new Room(null, null, true, 4, 2000.0);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> roomService.addRoom(room));
        assertEquals("Room type cannot be empty.", exception.getMessage());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testUpdateRoomSuccessfully() {
        // Arrange
        Room room = new Room(1L, "Deluxe", true, 4, 2500.0);
        when(roomRepository.existsById(1L)).thenReturn(true);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // Act
        Room updatedRoom = roomService.updateRoom(room);

        // Assert
        assertNotNull(updatedRoom);
        assertEquals("Deluxe", updatedRoom.getType());
        assertEquals(2500.0, updatedRoom.getPrice());
        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testUpdateRoomNotFound() {
        // Arrange
        Room room = new Room(1L, "Suite", true, 3, 1500.0);
        when(roomRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> roomService.updateRoom(room));
        assertEquals("Room not found.", exception.getMessage());
        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testDeleteRoomSuccessfully() {
        // Arrange
        when(roomRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roomRepository).deleteById(1L);

        // Act
        roomService.deleteRoom(1L);

        // Assert
        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRoomNotFound() {
        // Arrange
        when(roomRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> roomService.deleteRoom(1L));
        assertEquals("Room not found.", exception.getMessage());
        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetRoomByIdSuccessfully() {
        // Arrange
        Room room = new Room(1L, "Suite", true, 3, 1800.0);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Act
        Room fetchedRoom = roomService.getRoomById(1L);

        // Assert
        assertNotNull(fetchedRoom);
        assertEquals("Suite", fetchedRoom.getType());
        assertEquals(3, fetchedRoom.getCapacity());
        assertEquals(1800.0, fetchedRoom.getPrice());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRoomByIdNotFound() {
        // Arrange
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Room fetchedRoom = roomService.getRoomById(1L);

        // Assert
        assertNull(fetchedRoom);
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testIsRoomAvailableSuccessfully() {
        // Arrange
        Room room = new Room(1L, "Suite", true, 2, 1200.0);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Act
        boolean isAvailable = roomService.isRoomAvailable(1L, null, null);

        // Assert
        assertTrue(isAvailable);
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testIsRoomAvailableNotAvailable() {
        // Arrange
        Room room = new Room(1L, "Standard", false, 2, 1000.0);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Act
        boolean isAvailable = roomService.isRoomAvailable(1L, null, null);

        // Assert
        assertFalse(isAvailable);
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAvailableRoomsSuccessfully() {
        // Arrange
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1L, "Deluxe", true, 4, 2000.0));
        rooms.add(new Room(2L, "Suite", true, 2, 3000.0));

        when(roomRepository.findByAvailableTrue()).thenReturn(rooms);

        // Act
        List<Room> availableRooms = roomService.getAvailableRooms();

        // Assert
        assertNotNull(availableRooms);
        assertEquals(2, availableRooms.size());
        verify(roomRepository, times(1)).findByAvailableTrue();
    }
}
