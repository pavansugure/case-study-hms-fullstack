package com.reserv.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserv.client.GuestServiceClient;
import com.reserv.client.RoomServiceClient;
import com.reserv.model.Guest;
import com.reserv.model.Reservation;
import com.reserv.model.Room;
import com.reserv.repo.ReservRepository;
import com.reserv.service.ReservService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservServiceImpl implements ReservService {

    private static final Logger logger = LoggerFactory.getLogger(ReservServiceImpl.class);
	
    @Autowired
    private ReservRepository reservationRepository;

    @Autowired
    private RoomServiceClient roomServiceClient;

    @Autowired
    private GuestServiceClient guestServiceClient;

    @Override
    public Reservation makeReservation(Reservation reservation) {
        logger.debug("Validating reservation details: {}", reservation);

        if (reservation.getCheckOutDate().isBefore(reservation.getCheckInDate())) {
            logger.error("Validation failed: Check-out date is before check-in date.");
            throw new RuntimeException("Check-out date must be after the check-in date.");
        }

        Guest guest = guestServiceClient.getGuestById(reservation.getGuestId());
        if (guest == null) {
            logger.error("Validation failed: Guest not found with ID {}", reservation.getGuestId());
            throw new RuntimeException("Guest not found!");
        }

        // Convert dates to string format for Feign client
        String checkInDateStr = reservation.getCheckInDate().toString();  // "yyyy-MM-dd"
        String checkOutDateStr = reservation.getCheckOutDate().toString();

        boolean isAvailable = roomServiceClient.isRoomAvailable(
            reservation.getRoomId(),
            checkInDateStr,
            checkOutDateStr
        );
        if (!isAvailable) {
            logger.error("Validation failed: Room is not available for the given dates.");
            throw new RuntimeException("Room not available!");
        }

        int numberOfNights = (int) ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
        reservation.setNumberOfNights(numberOfNights);

        Room room = roomServiceClient.getRoomById(reservation.getRoomId());
        if (room == null) {
            logger.error("Room details not found for room id: {}", reservation.getRoomId());
            throw new RuntimeException("Room details not found!");
        }

        Double computedAmount = room.getPrice() * numberOfNights;
        reservation.setAmount(computedAmount);

        reservation.setStatus("CONFIRMED");

        logger.info("Reservation created successfully: {}", reservation);
        return reservationRepository.save(reservation);
    }


    @Override
    public List<Reservation> searchReservations(LocalDate startDate, LocalDate endDate) {
        logger.debug("Searching reservations for the provided date range.");
        return reservationRepository.findByCheckInDateBetween(startDate, endDate);
    }
    
    @Override
    public void deleteReservation(String code) {
        logger.info("Attempting to delete reservation with code: {}", code);

        try {
            reservationRepository.deleteById(code);
            logger.info("Reservation with code '{}' deleted successfully.", code);
        } catch (Exception e) {
            logger.error("Error occurred while deleting reservation with code '{}': {}", code, e.getMessage());
            throw new RuntimeException("Failed to delete reservation. Reason: " + e.getMessage());
        }
    }

    @Override
    public Reservation getReservationByCode(String code) {
        return reservationRepository.findById(code).orElse(null);
    }

	@Override
	public List<Reservation> getAllReservation() {
		return reservationRepository.findAll();
	}

}
