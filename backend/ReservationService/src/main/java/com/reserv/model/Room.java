package com.reserv.model;

import java.time.LocalDate;

public class Room {
	
    private Long id;
    private String type;
    private Boolean available;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
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
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Room(Long id, String type, Boolean available, LocalDate checkInDate, LocalDate checkOutDate, Double price) {
		super();
		this.id = id;
		this.type = type;
		this.available = available;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.price = price;
	}
   
}