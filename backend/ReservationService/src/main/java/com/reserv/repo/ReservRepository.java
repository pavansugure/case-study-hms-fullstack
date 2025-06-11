package com.reserv.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reserv.model.Reservation;

@Repository
public interface ReservRepository extends JpaRepository<Reservation, String> {
    // Query to find reservations within a period
    List<Reservation> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
}