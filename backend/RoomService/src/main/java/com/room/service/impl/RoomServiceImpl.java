package com.room.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.room.entity.Room;
import com.room.repo.RoomRepository;
import com.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
	
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addRoom(Room room) {
        if (room.getType() == null || room.getType().isEmpty()) {
            throw new RuntimeException("Room type cannot be empty.");
        }
        logger.debug("Saving room details to the database: {}", room);
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        if (!roomRepository.existsById(room.getId())) {
            throw new RuntimeException("Room not found.");
        }
        logger.debug("Updating room details in the database: {}", room);
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found.");
        }
        logger.warn("Deleting room with ID: {}", id);
        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomById(Long id) {
        logger.info("Fetching details for room ID: {}", id);
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Room room = getRoomById(roomId);
        if (room == null || !room.getAvailable()) {
            return false;
        }
        return true; // Extend this logic with reservation checks if necessary
    }

	@Override
	public List<Room> getAvailableRooms() {
		return roomRepository.findByAvailableTrue();
	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}
