package com.staff.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.staff.exception.ResourceNotFoundException;
import com.staff.model.Staff;
import com.staff.repo.StaffRepository;
import com.staff.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    @Autowired
    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff createStaff(Staff staff) {
        logger.info("Attempting to create staff with details: {}", staff);
        Staff createdStaff = staffRepository.save(staff);
        logger.info("Staff created successfully with ID: {}", createdStaff.getCode());
        return createdStaff;
    }

    @Override
    public Staff getStaffByCode(Long code) {
        logger.info("Fetching staff with ID: {}", code);
        return staffRepository.findById(code).orElseThrow(() -> {
            logger.error("Staff not found with ID: {}", code);
            return new ResourceNotFoundException("Staff not found with code: " + code);
        });
    }

    @Override
    public List<Staff> getAllStaff() {
        logger.info("Fetching all staff members from the database.");
        List<Staff> staffList = staffRepository.findAll();
        logger.info("Fetched {} staff members from the database.", staffList.size());
        return staffList;
    }

    @Override
    public void deleteStaff(Long code) {
        logger.info("Attempting to delete staff with ID: {}", code);
        if (!staffRepository.existsById(code)) {
            logger.error("Failed to delete staff: Staff not found with ID: {}", code);
            throw new ResourceNotFoundException("Staff not found with code: " + code);
        }
        staffRepository.deleteById(code);
        logger.info("Staff with ID {} deleted successfully.", code);
    }
    
    @Override
    public Staff updateStaff(Staff staff) {
        if (!staffRepository.existsById(staff.getCode())) {
            throw new RuntimeException("staff not found.");
        }
        logger.info("Updating staff details for ID {}: {}", staff.getCode(), staff);
        return staffRepository.save(staff);
    }
}
