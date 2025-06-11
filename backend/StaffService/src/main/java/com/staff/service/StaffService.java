package com.staff.service;

import java.util.List;

import com.staff.model.Staff;

public interface StaffService {

	Staff createStaff(Staff staff);
	Staff getStaffByCode(Long code);
	List<Staff> getAllStaff();
	void deleteStaff(Long code);
	Staff updateStaff(Staff staff);
}
