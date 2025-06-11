package com.reserv.controller;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reserv.model.Reservation;
import com.reserv.service.ReservService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reservations")
public class ReservController {

    private static final Logger logger = LoggerFactory.getLogger(ReservController.class);
	
    @Autowired
    private ReservService reservationService;
    
    @GetMapping("/{code}")
    public Reservation getReservationByCode(@PathVariable String code) {
        logger.info("Fetching reservation details for code: {}", code);
        return reservationService.getReservationByCode(code);
    }

    @PostMapping
    public Reservation makeReservation(@Valid @RequestBody Reservation reservation) {
    	logger.info("Creating reservation: {}", reservation);
        return reservationService.makeReservation(reservation);
    }

    @GetMapping("/search")
    public List<Reservation> searchReservations(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        logger.info("Searching reservations between {} and {}", startDate, endDate);
        return reservationService.searchReservations(startDate, endDate);
    }
    
    @DeleteMapping("/{code}")
    public void deleteReservation(@PathVariable String code) {
        logger.info("Request received to delete reservation with code: {}", code);

        try {
            reservationService.deleteReservation(code);
            logger.info("Reservation with code '{}' deleted successfully.", code);
        } catch (Exception e) {
            logger.error("Error occurred while deleting reservation with code '{}': {}", code, e.getMessage());
            throw e; 
        }
    }
    
    @GetMapping
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }
}