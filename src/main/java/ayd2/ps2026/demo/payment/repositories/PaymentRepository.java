package ayd2.ps2026.demo.payment.repositories;

import ayd2.ps2026.demo.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
