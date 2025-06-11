package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.entity.Reservation;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationServiceClient {

    @GetMapping("/reservations/{code}")
    Reservation getReservationByCode(@PathVariable String code);
}
