package com.razorpay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.razorpay.model.Reservation;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationClient {
    @GetMapping("/reservations/{code}")
    Reservation getReservationByCode(@PathVariable String code);
}
