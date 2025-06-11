package com.staff.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.staff.model.Staff;
import com.staff.service.StaffService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/staff")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
    private final StaffService staffService; 

    public StaffController(StaffService staffService) {
        this.staffService = staffService; 
    }
    
    @PostMapping("/add")
    public Staff createStaff(@Valid @RequestBody Staff staff) {
        try {
            logger.info("Received request to create staff: {}", staff);
            Staff createdStaff = staffService.createStaff(staff);
            logger.info("Staff created successfully: {}", createdStaff);
            return createdStaff;
        } catch(Exception ex) {
            logger.error("Error occurred while creating staff: {}", ex.getMessage(), ex);
      
            throw new RuntimeException("An error occurred while creating staff.");
        }
    }
    
    @GetMapping("/{code}")
    public Staff getStaffByCode(@PathVariable Long code) {
        try {
            logger.info("Fetching staff with code: {}", code);
            Staff staff = staffService.getStaffByCode(code);
            if (staff == null) {
                logger.warn("No staff found with code: {}", code);
                throw new RuntimeException("No staff found with code: " + code);
            }
            logger.info("Staff found: {}", staff);
            return staff;
        } catch(Exception ex) {
            logger.error("Error occurred while fetching staff: {}", ex.getMessage(), ex);
            throw new RuntimeException("An error occurred while fetching staff.");
        }
    }
    
    @GetMapping
    public List<Staff> getAllStaff(){
        try {
            logger.info("Fetching all staff records");
            List<Staff> staffList = staffService.getAllStaff();
            logger.info("Total staff records found: {}", staffList.size());
            return staffList;
        } catch(Exception ex) {
            logger.error("Error occurred while fetching all staff: {}", ex.getMessage(), ex);
            throw new RuntimeException("An error occurred while fetching all staff.");
        }
    }
    
    @DeleteMapping("/{code}")
    public void deleteStaff(@PathVariable Long code) {
        try {
            logger.info("Deleting staff with code: {}", code);
            staffService.deleteStaff(code);
            logger.info("Staff with code {} deleted successfully", code);
        } catch(Exception ex) {
            logger.error("Error occurred while deleting staff: {}", ex.getMessage(), ex);
            throw new RuntimeException("An error occurred while deleting staff.");
        }
    }
    
    @PutMapping
    public Staff updateStaff(@Valid @RequestBody Staff staff) {
        logger.info("Updating guest details for ID {}: {}", staff.getCode(), staff);
        return staffService.updateStaff(staff);
    }
}
