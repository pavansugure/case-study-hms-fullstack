package com.razorpay.dto;

import java.time.LocalDate;

public class InvoiceDetailsDto {
	private String guestName;
	private String email;
	private String address;
	private String phoneNo;

	private LocalDate checkInDate;
	private LocalDate checkOutDate;

	private String roomType;
	private double amount;
	private String code;
	
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public InvoiceDetailsDto(String guestName, String email, String address, String phoneNo, LocalDate checkInDate,
			LocalDate checkOutDate, String roomType, double amount, String code) {
		super();
		this.guestName = guestName;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.amount = amount;
		this.code = code;
	}
	
	public InvoiceDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
