package com.razorpay.controller;

import com.razorpay.client.GuestClient;
import com.razorpay.client.ReservationClient;
import com.razorpay.client.RoomClient;
import com.razorpay.dto.InvoiceDetailsDto;
import com.razorpay.model.Guest;
import com.razorpay.model.Orders;
import com.razorpay.model.Reservation;
import com.razorpay.model.Room;
import com.razorpay.repo.OrdersRepository;
import com.razorpay.service.InvoiceService;
import com.razorpay.service.OrderService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/pay")
public class OrdersController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private ReservationClient reservationClient;
    
    @Autowired
    private GuestClient guestClient;
    
    @Autowired
    private RoomClient roomClient;
    
    @GetMapping("/orders")
    public String ordersPage() {
        return "orders";
    }
    
    @PostMapping(value = "/createOrder/{reservationCode}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable String reservationCode) {
        try {
            Orders razorpayOrder = orderService.createOrder(reservationCode);

            Map<String, Object> response = Map.of(
                "orderId", razorpayOrder.getRazorpayOrderId(),
                "amount", razorpayOrder.getAmount()  // Send amount in paise
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Failed to create Razorpay order"));
        }
    }
                                                                    
    
    @PostMapping("/paymentCallback")
    public String paymentCallback(@RequestParam Map<String, String> response) {
        orderService.updateStatus(response);
        return "success";
    }
    
    @GetMapping("/paymentSuccess")
    public String paymentSuccessPage() {
        return "success";
    }
    
    @GetMapping("/downloadInvoice/{razorpayOrderId}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable String razorpayOrderId) throws FileNotFoundException {
        Orders order = ordersRepository.findByRazorpayOrderId(razorpayOrderId);
        if (order == null) {
            throw new RuntimeException("Order not found for Razorpay ID: " + razorpayOrderId);
        }

        if (order.getInvoiceFilePath() == null || order.getInvoiceFilePath().isEmpty()) {
            System.out.println("Invoice not found. Generating invoice...");
            String generatedPath = invoiceService.generateInvoice(order);
            order.setInvoiceFilePath(generatedPath);
            ordersRepository.save(order);
        }

        File file = new File(order.getInvoiceFilePath());
        if (!file.exists()) {
            throw new FileNotFoundException("Invoice file does not exist at: " + order.getInvoiceFilePath());
        }

        return ResponseEntity.ok()
        	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + razorpayOrderId + "_invoice.pdf")
            .body(new InputStreamResource(new FileInputStream(file)));
    }
    
    @GetMapping("/details/{reservationCode}")
    public ResponseEntity<InvoiceDetailsDto> getPaymentDetails(@PathVariable String reservationCode) {

        Reservation reservation = reservationClient.getReservationByCode(reservationCode);
        Guest guest = guestClient.getGuestById(reservation.getGuestId());
        Room room = roomClient.getRoomById(reservation.getRoomId());

        InvoiceDetailsDto response = new InvoiceDetailsDto();
        response.setGuestName(guest.getGuestName());
        response.setEmail(guest.getEmail());
        response.setAddress(guest.getAddress());
        response.setPhoneNo(guest.getPhoneNo());

        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());

        response.setRoomType(room.getType());
        response.setAmount(reservation.getAmount());

        return ResponseEntity.ok(response);
    }

}
