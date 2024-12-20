package com.hexaware.CozyHavenStay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.CozyHavenStay.dto.PaymentRequestDTO;
import com.hexaware.CozyHavenStay.dto.PaymentResponseDTO;
import com.hexaware.CozyHavenStay.model.Payment;
import com.hexaware.CozyHavenStay.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        // Convert DTO to entity
        Payment payment = new Payment();
        payment.setBookingId(paymentRequest.getBookingId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setTransaction_id(paymentRequest.getTransaction_id());

        // Save payment
        Payment createdPayment = paymentService.createPayment(payment);

        // Convert entity to DTO
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(createdPayment.getId());
        response.setBookingId(createdPayment.getBookingId());
        response.setAmount(createdPayment.getAmount());
        response.setPaymentMethod(createdPayment.getPaymentMethod());
        response.setPaymentDate(createdPayment.getPaymentDate());
        response.setTransaction_id(paymentRequest.getTransaction_id());


        return ResponseEntity.ok(response);
    }
}
