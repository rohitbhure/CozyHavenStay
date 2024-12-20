package com.hexaware.CozyHavenStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.model.Payment;
import com.hexaware.CozyHavenStay.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(new java.util.Date());
        return paymentRepository.save(payment);
    }
}
