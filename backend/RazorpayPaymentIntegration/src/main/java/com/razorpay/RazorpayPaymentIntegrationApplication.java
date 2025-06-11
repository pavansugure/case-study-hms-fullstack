package com.razorpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RazorpayPaymentIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RazorpayPaymentIntegrationApplication.class, args);
	}

}
