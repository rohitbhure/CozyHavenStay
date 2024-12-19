package com.hexaware.CozyHavenStay.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Long id;
    private String transactionId;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private Long bookingId;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, String transactionId, double amount, String paymentMethod, LocalDateTime paymentDate, Long bookingId) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.bookingId = bookingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
