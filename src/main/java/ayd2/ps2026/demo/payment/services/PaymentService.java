package ayd2.ps2026.demo.payment.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.payment.dtos.request.CreatePaymentDTO;
import ayd2.ps2026.demo.payment.dtos.response.PaymentDTO;
import ayd2.ps2026.demo.payment.models.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    Payment registerPayment(CreatePaymentDTO createPaymentDTO) throws NotFoundException;
    List<PaymentDTO> getAllOwnedPayments(LocalDate init, LocalDate end);
    List<PaymentDTO> getAllPayments(LocalDate init, LocalDate end);
}
