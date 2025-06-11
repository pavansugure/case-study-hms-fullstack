package com.reserv.service;
import java.time.LocalDate;
import java.util.List;

import com.reserv.model.Reservation;

public interface ReservService {
	Reservation getReservationByCode(String code);
    Reservation makeReservation(Reservation reservation);
    List<Reservation> searchReservations(LocalDate checkInDate, LocalDate checkOutDate);
	void deleteReservation(String code);
	List<Reservation> getAllReservation();
}

