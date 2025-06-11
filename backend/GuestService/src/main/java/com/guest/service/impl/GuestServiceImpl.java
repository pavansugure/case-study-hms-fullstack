package com.guest.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guest.controller.GuestController;
import com.guest.model.Guest;
import com.guest.repo.GuestRepository;
import com.guest.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

    private static final Logger logger = LoggerFactory.getLogger(GuestController.class);
	
    @Autowired
    private GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
		// TODO Auto-generated constructor stub
    	this.guestRepository=guestRepository;
	}

	@Override
    public Guest addGuest(Guest guest) {
        if (guestRepository.existsById(guest.getMemberCode())) {
            throw new RuntimeException("Guest with MemberCode already exists.");
        }
        logger.debug("Saving guest details to the database: {}", guest);
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Guest guest) {
        if (!guestRepository.existsById(guest.getMemberCode())) {
            throw new RuntimeException("Guest not found.");
        }
        logger.info("Updating guest details for ID {}: {}", guest.getMemberCode(), guest);
        return guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long memberCode) {
        if (!guestRepository.existsById(memberCode)) {
            throw new RuntimeException("Guest not found.");
        }
        logger.warn("Deleting guest with ID: {}", memberCode);
        guestRepository.deleteById(memberCode);
    }

    @Override
    public Guest getGuestById(Long memberCode) {
        logger.info("Fetching details for guest ID: {}", memberCode);
        return guestRepository.findById(memberCode).orElse(null);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }
}