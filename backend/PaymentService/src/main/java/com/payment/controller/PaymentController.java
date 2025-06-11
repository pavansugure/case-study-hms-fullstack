package com.payment.controller;

import com.payment.entity.Payment;
import com.payment.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public Payment addPayment(@Valid @RequestBody Payment payment) {
        logger.info("Received request to add payment: {}", payment);
        return paymentService.addPayment(payment);
    }

    //@PostMapping("/issue-bill/{paymentCode}")
    //@PreAuthorize("hasRole('OWNER') or hasRole('RECEPTIONIST')")
    //public void issueBill(@PathVariable Long paymentCode) {
    //    logger.info("Received request to issue bill for payment code: {}", paymentCode);
    //    paymentService.issueBill(paymentCode);
    //}
}
