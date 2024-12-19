package com.hexaware.CozyHavenStay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.CozyHavenStay.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
