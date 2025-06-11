package com.razorpay.service;

import com.razorpay.client.GuestClient;
import com.razorpay.client.ReservationClient;
import com.razorpay.client.RoomClient;
import com.razorpay.model.Guest;
import com.razorpay.model.Orders;
import com.razorpay.model.Reservation;
import com.razorpay.model.Room;
import com.razorpay.repo.OrdersRepository;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    private ReservationClient reservationClient;  // Using our Feign client
    
    @Value("${razorpay.key.id}")
    private String razorpayId;
    
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;
    
    private RazorpayClient razorpayClient;
    
    @Autowired
    private GuestClient guestClient;

    @Autowired
    private RoomClient roomClient;
    
    @PostConstruct
    public void init() throws RazorpayException {
        this.razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);
    }
    
    // Modified createOrder method to accept a reservation code
    public Orders createOrder(String reservationCode) throws RazorpayException, JsonProcessingException {
        Reservation reservation = reservationClient.getReservationByCode(reservationCode);
        System.out.println("Creating Razorpay order for reservation: " + reservationCode);

        Double amount = reservation.getAmount();
        String code = reservation.getCode();

        // Fetch guest and room details
        Guest guest = guestClient.getGuestById(reservation.getGuestId());
        Room room = roomClient.getRoomById(reservation.getRoomId());
        System.out.println("Received Guest: " + new ObjectMapper().writeValueAsString(guest));

        Orders order = new Orders();
        order.setAmount(amount);

        // Set guest details
        order.setGuestName(guest.getGuestName());
        order.setEmail(guest.getEmail());
        order.setPhoneNo(guest.getPhoneNo());
        order.setAddress(guest.getAddress());

        // Set room and reservation dates
        order.setRoomType(room.getType());
        order.setCheckinDate(reservation.getCheckInDate());
        order.setCheckoutDate(reservation.getCheckOutDate());

        // Razorpay Order creation
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", code);

        Order razorpayOrder = razorpayClient.orders.create(options);
        if (razorpayOrder != null) {
            order.setRazorpayOrderId(razorpayOrder.get("id"));
            order.setOrderStatus(razorpayOrder.get("status"));
        }

        return ordersRepository.save(order);
    }


    @Autowired
    private InvoiceService invoiceService;

    public Orders updateStatus(Map<String, String> paymentResponse) {
        String razorpayId = paymentResponse.get("razorpay_order_id");

        Orders order = ordersRepository.findByRazorpayOrderId(razorpayId);
        if (order == null) {
            throw new RuntimeException("Order not found for Razorpay ID: " + razorpayId);
        }

        order.setOrderStatus("PAYMENT DONE");

        if (order.getInvoiceFilePath() == null || order.getInvoiceFilePath().isEmpty()) {
            System.out.println("Generating invoice for id : "+razorpayId);
            String invoicePath = invoiceService.generateInvoice(order);
            order.setInvoiceFilePath(invoicePath);
            ordersRepository.save(order);
        }

        return ordersRepository.save(order);
    }


}
