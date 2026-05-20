package ayd2.ps2026.demo.payment.mappers;

import ayd2.ps2026.demo.payment.dtos.request.CreatePaymentDTO;
import ayd2.ps2026.demo.payment.dtos.response.PaymentDTO;
import ayd2.ps2026.demo.payment.models.Payment;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentMapper {
    Payment createPaymentDtoToPayment(CreatePaymentDTO createPaymentDTO);
    PaymentDTO paymentToPaymentDto(Payment payment);
    List<PaymentDTO> paymentToPaymentDto(List<Payment> payments);
}
