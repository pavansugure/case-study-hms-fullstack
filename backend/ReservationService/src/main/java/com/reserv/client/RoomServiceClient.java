package com.reserv.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomServiceClient {

    @GetMapping("/rooms/{id}")
    com.reserv.model.Room getRoomById(@PathVariable Long id);

    @GetMapping("/rooms/available")
    boolean isRoomAvailable(@RequestParam Long roomId,
                            @RequestParam String checkInDate,
                            @RequestParam String checkOutDate);

    @GetMapping("/rooms/available-rooms")
    List<com.reserv.model.Room> getAvailableRooms();
}
