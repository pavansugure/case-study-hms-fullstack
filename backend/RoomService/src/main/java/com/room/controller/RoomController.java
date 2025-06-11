package com.room.controller;

import com.room.entity.Room;
import com.room.service.RoomService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    // Add a new room
    @PostMapping
    public Room addRoom(@Valid @RequestBody Room room) {
        try {
            logger.info("Adding a new room: {}", room);
            Room createdRoom = roomService.addRoom(room);
            logger.info("Room added successfully with ID: {}", createdRoom.getId());
            return createdRoom;
        } catch (Exception e) {
            logger.error("Error occurred while adding room: {}", e.getMessage(), e);
            throw new RuntimeException("Error adding room: " + e.getMessage());
        }
    }

    // Update room details
    @PutMapping
    public Room updateRoom(@Valid @RequestBody Room room) {
        try {
            logger.info("Updating room with ID {}: {}", room.getId(), room);
            Room updatedRoom = roomService.updateRoom(room);
            logger.info("Room updated successfully: {}", updatedRoom);
            return updatedRoom;
        } catch (Exception e) {
            logger.error("Error occurred while updating room with ID {}: {}", room.getId(), e.getMessage(), e);
            throw new RuntimeException("Error updating room with ID " + room.getId() + ": " + e.getMessage());
        }
    }

    // Delete a room by ID
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        try {
            logger.warn("Request received to delete room with ID: {}", id);
            roomService.deleteRoom(id);
            logger.info("Room with ID {} deleted successfully.", id);
        } catch (Exception e) {
            logger.error("Failed to delete room with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting room: " + e.getMessage());
        }
    }

    // Get room by ID
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        try {
            logger.info("Fetching details for room ID: {}", id);
            Room room = roomService.getRoomById(id);
            if (room == null) {
                logger.warn("Room with ID {} not found.", id);
                throw new RuntimeException("Room with ID " + id + " not found.");
            }
            logger.info("Room found: {}", room);
            return room;
        } catch (Exception e) {
            logger.error("Error occurred while fetching room with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching room with ID " + id + ": " + e.getMessage());
        }
    }

    // Check if a room is available for a given period
    @GetMapping("/available")
    public boolean isRoomAvailable(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {
        try {
            logger.info("Checking room availability for Room ID: {}, Check-In: {}, Check-Out: {}",
                    roomId, checkInDate, checkOutDate);
            boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
            logger.info("Room availability status for Room ID {}: {}", roomId, isAvailable);
            return isAvailable;
        } catch (Exception e) {
            logger.error("Error checking availability for Room ID {}: {}", roomId, e.getMessage(), e);
            throw new RuntimeException("Error checking room availability: " + e.getMessage());
        }
    }

    // Get all available rooms
    @GetMapping("/available-rooms")
    public List<Room> getAvailableRooms() {
        try {
            logger.info("Fetching all available rooms.");
            List<Room> availableRooms = roomService.getAvailableRooms();
            logger.info("Total available rooms found: {}", availableRooms.size());
            return availableRooms;
        } catch (Exception e) {
            logger.error("Error fetching available rooms: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching available rooms: " + e.getMessage());
        }
    }
    
    @GetMapping
    public List<Room> getAllRooms() {
    	return roomService.getAllRooms();
    }
}
