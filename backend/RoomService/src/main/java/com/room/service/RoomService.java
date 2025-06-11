package com.room.service;

import java.time.LocalDate;
import java.util.List;

import com.room.entity.Room;

public interface RoomService {
	
    Room addRoom(Room room);
    Room updateRoom(Room room);
    void deleteRoom(Long id);
	Room getRoomById(Long id);
	boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
	List<Room> getAvailableRooms();
	List<Room> getAllRooms();
}