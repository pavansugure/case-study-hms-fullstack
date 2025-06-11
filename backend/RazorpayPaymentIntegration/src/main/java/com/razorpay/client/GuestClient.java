package com.razorpay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.razorpay.model.Guest;


@FeignClient(name = "GUEST-SERVICE")
public interface GuestClient {
    @GetMapping("/guests/{memberCode}")
    Guest getGuestById(@PathVariable Long memberCode);
}