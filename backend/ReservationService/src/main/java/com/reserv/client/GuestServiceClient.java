package com.reserv.client;

import com.reserv.model.Guest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "GUEST-SERVICE")
public interface GuestServiceClient {
    
	@GetMapping("/guests/{id}")
    Guest getGuestById(@PathVariable Long id);

    @PostMapping("/guests")
    Guest addGuest(@RequestBody Guest guest);
}
