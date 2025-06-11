package com.guest.service;

import java.util.List;

import com.guest.model.Guest;

public interface GuestService {
    Guest addGuest(Guest guest);
    Guest updateGuest(Guest guest); 
    void deleteGuest(Long memberCode); 
    Guest getGuestById(Long memberCode); 
    List<Guest> getAllGuests(); 
}