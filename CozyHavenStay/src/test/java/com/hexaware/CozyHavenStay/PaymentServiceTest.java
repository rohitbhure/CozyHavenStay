package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.model.Payment;
import com.hexaware.CozyHavenStay.repository.PaymentRepository;
import com.hexaware.CozyHavenStay.service.PaymentService;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        // Arrange
        Payment payment = new Payment();
        payment.setAmount(1000.0);
        payment.setPaymentMethod("Credit Card");

        Payment savedPayment = new Payment();
        savedPayment.setId(1L);
        savedPayment.setAmount(1000.0);
        savedPayment.setPaymentMethod("Credit Card");
        savedPayment.setPaymentDate(new Date());

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        // Act
        Payment result = paymentService.createPayment(payment);

        // Assert
        assertNotNull(result);
        assertEquals(1000.0, result.getAmount());
        assertEquals("Credit Card", result.getPaymentMethod());
        assertNotNull(result.getPaymentDate());
        verify(paymentRepository, times(1)).save(payment);
    }
}
