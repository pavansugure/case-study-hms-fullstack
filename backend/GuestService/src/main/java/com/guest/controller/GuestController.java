package com.guest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guest.model.Guest;
import com.guest.service.GuestService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/guests")
public class GuestController {

    private static final Logger logger = LoggerFactory.getLogger(GuestController.class);
 
    @Autowired
    private GuestService guestService;
 
    @PostMapping("/add")
    public Guest addGuest(@Valid @RequestBody Guest guest) {
        logger.info("Adding a new guest: {}", guest);
        return guestService.addGuest(guest);
    }

    @PutMapping
    public Guest updateGuest(@Valid @RequestBody Guest guest) {
        logger.info("Updating guest details for ID {}: {}", guest.getMemberCode(), guest);
        return guestService.updateGuest(guest);
    }

    // Delete a guest by ID
    @DeleteMapping("/{memberCode}")
    public void deleteGuest(@PathVariable Long memberCode) {
        logger.warn("Deleting guest with ID: {}", memberCode);
        guestService.deleteGuest(memberCode);
    }

    // Get a guest by ID
    @GetMapping("/{memberCode}")
    public Guest getGuestById(@PathVariable Long memberCode) {
        logger.info("Fetching details for guest ID: {}", memberCode);
        return guestService.getGuestById(memberCode);
    }

    // Get all guests
    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }
}