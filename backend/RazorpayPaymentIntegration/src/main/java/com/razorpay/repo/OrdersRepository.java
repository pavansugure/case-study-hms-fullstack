package com.razorpay.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.razorpay.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{

	Orders findByRazorpayOrderId(String razorpayId);

}
