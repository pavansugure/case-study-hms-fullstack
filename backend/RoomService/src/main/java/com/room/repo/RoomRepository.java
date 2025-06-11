package com.room.repo;

import com.room.entity.Room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Custom query to fetch all available rooms
    List<Room> findByAvailableTrue();
}
