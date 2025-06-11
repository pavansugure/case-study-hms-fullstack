package com.payment.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Payment code is required.")
    @Size(min = 3, max = 10, message = "Payment code must be 3-10 characters.")
    private String paymentCode;
    
    @NotBlank(message = "Reservation code is required.")
    @Size(min = 3, max = 10, message = "Reservation code must be 3-10 characters.")
    private String reservationCode;
    
    @Positive(message = "Total amount must be greater than zero.")
    @Positive(message = "Total amount must be greater than zero.")
    private Double totalAmount;
    
    @NotNull(message = "Payment date is required.")
    @FutureOrPresent(message = "payment date must be today or in the future.")
    private LocalDate paymentDate; 
    
    @NotBlank(message = "Payment method cannot be blank.")
    private String paymentMethod;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReservationCode() {
		return reservationCode;
	}
	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Payment(Long id, String paymentCode, String reservationCode, Double totalAmount, LocalDate paymentDate, String paymentMethod) {
		super();
		this.id = id;
		this.paymentCode = paymentCode;
		this.reservationCode = reservationCode;
		this.totalAmount = totalAmount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
	}
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	
}