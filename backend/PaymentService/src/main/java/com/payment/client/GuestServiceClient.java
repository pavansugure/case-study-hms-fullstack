package com.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.payment.entity.Guest;

@FeignClient(name = "GUEST-SERVICE")
public interface GuestServiceClient {
    @GetMapping("/guests/{memberCode}")
    Guest getGuestById(@PathVariable Long memberCode);
}
