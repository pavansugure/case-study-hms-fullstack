package com.reserv.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Reservation {
	
    @Id
    @NotBlank(message = "Reservation code is required.")
    private String code;
    
    @NotNull(message = "Guest ID is required.")
    private Long guestId;
    
    @NotNull(message = "Room ID is required.")
    private Long roomId; 

    @Min(value = 0, message = "Number of children cannot be negative.")
    private int numberOfChildren;
    
    @Min(value = 1, message = "There must be at least one adult.")
    private int numberOfAdults;
    
    @NotNull(message = "Check-in date is required.")
    @FutureOrPresent(message = "Check-in date must be today or in the future.")
    private LocalDate checkInDate;
    
    @NotNull(message = "Check-out date is required.")
    @Future(message = "Check-out date must be in the future.")
    private LocalDate checkOutDate;
    
    @NotBlank(message = "Status cannot be blank.")
    private String status;
    
    private int numberOfNights;
    
    private Double amount;
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getGuestId() {
		return guestId;
	}
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public int getNumberOfAdults() {
		return numberOfAdults;
	}
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}
	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Reservation(String code, Long guestId, Long roomId, int numberOfChildren, int numberOfAdults,
			LocalDate checkInDate, LocalDate checkOutDate, String status, int numberOfNights, Double amount) {
		super();
		this.code = code;
		this.guestId = guestId;
		this.roomId = roomId;
		this.numberOfChildren = numberOfChildren;
		this.numberOfAdults = numberOfAdults;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.status = status;
		this.numberOfNights = numberOfNights;
		this.amount = amount;
	}
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}	