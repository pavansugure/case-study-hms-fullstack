package com.guest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
//@Table(name = "guest")
public class Guest {

	@Id
    @NotNull(message = "Member code is required.")
	private Long memberCode;
	
    @NotBlank(message = "Guest name is required.")
	private String guestName;
    
    @NotNull(message = "Phone number is required.")
    @Min(value = 1000000000L, message = "There must be at least ten digits.")
	private Long phoneNo;
    
    @NotBlank(message = "company name is required")
	private String company;
	
	@NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
	private String email;
	
	@NotBlank(message = "Gender is required.")
	private String gender;
	
	@NotBlank(message = "Address is required.")
	private String address;
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Guest(long memberCode, long phoneNo, String company, String guestName, String email, String gender,
			String address) {
		super();
		this.memberCode = memberCode;
		this.phoneNo = phoneNo;
		this.company = company;
		this.guestName = guestName;
		this.email = email;
		this.gender = gender;
		this.address = address;
	}
	
	public Guest() {
		super();
	}
	
}