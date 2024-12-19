package com.hexaware.CozyHavenStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.model.Payment;
import com.hexaware.CozyHavenStay.repository.BookingRepository;
import com.hexaware.CozyHavenStay.repository.PaymentRepository;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Payment createPayment(Long bookingId, Payment paymentDetails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id " + bookingId));

        paymentDetails.setBooking(booking);
        paymentDetails.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(paymentDetails);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id " + id));
    }
}
