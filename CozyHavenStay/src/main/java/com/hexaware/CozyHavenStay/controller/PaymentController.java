package com.hexaware.CozyHavenStay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hexaware.CozyHavenStay.dto.PaymentDTO;
import com.hexaware.CozyHavenStay.model.Payment;
import com.hexaware.CozyHavenStay.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<Map<String, Object>> createPayment(@PathVariable Long bookingId, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());

        Payment savedPayment = paymentService.createPayment(bookingId, payment);

        Map<String, Object> response = new HashMap<>();
        response.put("transactionId", savedPayment.getTransactionId());
        response.put("amount", savedPayment.getAmount());
        response.put("paymentDate", savedPayment.getPaymentDate());
        response.put("message", "Payment successfully processed.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        PaymentDTO paymentDTO = new PaymentDTO(
                payment.getId(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getBooking().getId()
        );

        return ResponseEntity.ok(paymentDTO);
    }
}
