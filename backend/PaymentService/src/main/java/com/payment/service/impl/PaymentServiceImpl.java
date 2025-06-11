package com.payment.service.impl;

import com.payment.entity.Payment;
import com.payment.entity.Reservation;
import com.payment.repo.PaymentRepo;
import com.payment.service.PaymentService;
import com.payment.client.ReservationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private ReservationServiceClient reservationServiceClient;

    @Override
    public Payment addPayment(Payment payment) {
        logger.info("Attempting to add a new payment: {}", payment);

        // Check for negative totalAmount
        if (payment.getTotalAmount() <= 0) {
            logger.error("Failed to add payment due to invalid total amount: {}", payment.getTotalAmount());
            throw new RuntimeException("Total amount must be greater than zero!");
        }

        // Validate Reservation
        Reservation reservation = reservationServiceClient.getReservationByCode(payment.getReservationCode());
        if (reservation == null) {
            logger.error("Failed to find reservation with code: {}", payment.getReservationCode());
            throw new RuntimeException("Reservation not found!");
        }
        logger.info("Reservation validated successfully for payment.");

        // Save Payment
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment added successfully: {}", savedPayment);
        return savedPayment;
    }

    /*
    @Override
    public void issueBill(Long paymentCode) {
        logger.info("Generating bill for paymentCode: {}", paymentCode);

        // Retrieve Payment
        Payment payment = paymentRepository.findById(paymentCode).orElse(null);
        if (payment == null) {
            logger.error("Failed to find payment with code: {}", paymentCode);
            throw new RuntimeException("Payment not found!");
        }

        // Example bill generation logic (replace with actual implementation)
        logger.info("Bill issued successfully for payment: {}", payment);
    }
    */
}
