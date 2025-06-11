package com.payment.entity;

public class Guest {

	private Long memberCode;
	private String guestName;
	private Long phoneNo;
	private String company;
	private String email;
	private String gender;
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