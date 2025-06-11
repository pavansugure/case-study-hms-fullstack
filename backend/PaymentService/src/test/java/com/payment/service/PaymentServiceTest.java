package com.payment.service;

import com.payment.entity.Payment;
import com.payment.entity.Reservation;
import com.payment.repo.PaymentRepo;
import com.payment.client.ReservationServiceClient;
import com.payment.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private ReservationServiceClient reservationServiceClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testAddPaymentSuccessfully() {
        // Arrange
        Payment payment = new Payment(null, "PAY123", "RES123", 500.0,
                LocalDate.now(), "Credit Card");
        Reservation reservation = new Reservation("RES123", 1L, 101L, 2, 2,
                LocalDate.now(), LocalDate.now().plusDays(3), "CONFIRMED", 3);

        when(reservationServiceClient.getReservationByCode("RES123")).thenReturn(reservation);
        when(paymentRepo.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            savedPayment.setId(1L); // Simulate setting ID during save
            return savedPayment;
        });

        // Act
        Payment savedPayment = paymentService.addPayment(payment);

        // Assert
        assertNotNull(savedPayment);
        assertNotNull(savedPayment.getId(), "Payment ID should not be null after saving.");
        assertEquals("PAY123", savedPayment.getPaymentCode());
        assertEquals("RES123", savedPayment.getReservationCode());
        assertEquals("Credit Card", savedPayment.getPaymentMethod());
        verify(reservationServiceClient, times(1)).getReservationByCode("RES123");
        verify(paymentRepo, times(1)).save(payment);
    }

    @Test
    void testAddPaymentWithInvalidReservation() {
        // Arrange
        Payment payment = new Payment(null, "PAY123", "INVALID", 500.0,
                LocalDate.now(), "Credit Card");

        when(reservationServiceClient.getReservationByCode("INVALID")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> paymentService.addPayment(payment));
        assertEquals("Reservation not found!", exception.getMessage());
        verify(reservationServiceClient, times(1)).getReservationByCode("INVALID");
        verify(paymentRepo, never()).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithFuturePaymentDate() {
        // Arrange
        Payment payment = new Payment(null, "PAY456", "RES456", 700.0,
                LocalDate.now().plusDays(1), "Debit Card");
        Reservation reservation = new Reservation("RES456", 2L, 102L, 0, 2,
                LocalDate.now(), LocalDate.now().plusDays(3), "CONFIRMED", 3);

        when(reservationServiceClient.getReservationByCode("RES456")).thenReturn(reservation);
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment savedPayment = paymentService.addPayment(payment);

        // Assert
        assertNotNull(savedPayment);
        assertEquals(LocalDate.now().plusDays(1), savedPayment.getPaymentDate());
        verify(reservationServiceClient, times(1)).getReservationByCode("RES456");
        verify(paymentRepo, times(1)).save(payment);
    }

}
