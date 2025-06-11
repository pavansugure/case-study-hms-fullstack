package com.razorpay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.razorpay.model.Room;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomClient {
    @GetMapping("/rooms/{id}")
    Room getRoomById(@PathVariable Long id);
}