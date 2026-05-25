package ayd2.ps2026.demo.payment.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.constants.enums.ConstantsEnum;
import ayd2.ps2026.demo.constants.models.Constant;
import ayd2.ps2026.demo.constants.services.ConstantService;
import ayd2.ps2026.demo.payment.dtos.request.CreatePaymentDTO;
import ayd2.ps2026.demo.payment.dtos.response.PaymentDTO;
import ayd2.ps2026.demo.payment.mappers.PaymentMapper;
import ayd2.ps2026.demo.payment.models.Payment;
import ayd2.ps2026.demo.payment.repositories.PaymentRepository;
import ayd2.ps2026.demo.wallet.models.Wallet;
import ayd2.ps2026.demo.wallet.repositories.WalletRepository;
import ayd2.ps2026.demo.wallet.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    private static final Integer WALLET_ID = 1;
    private static final Integer USER_ID = 2;
    private static final Float WALLET_CURRENCY = 100f;

    private static final Float PAYMENT_AMOUNT = 100f;
    private static final Float COMMISSION_PERCENTAGE = 0.10f;
    private static final Float COMMISSION = 10f;
    private static final Float REAL_AMOUNT = 90f;

    private static final LocalDate INIT_DATE =
            LocalDate.of(2026, 1, 1);

    private static final LocalDate END_DATE =
            LocalDate.of(2026, 1, 31);

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private WalletService walletService;

    @Mock
    private ConstantService constantService;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void registerPayment_success() throws NotFoundException {

        CreatePaymentDTO dto = new CreatePaymentDTO();
        dto.setAmount(PAYMENT_AMOUNT);

        Wallet wallet = new Wallet(USER_ID, WALLET_CURRENCY);
        wallet.setId(WALLET_ID);

        Payment payment = new Payment();

        Constant constant = new Constant();
        constant.setValue(COMMISSION_PERCENTAGE);

        Payment savedPayment = new Payment();
        savedPayment.setAmount(REAL_AMOUNT);
        savedPayment.setCommission(COMMISSION);

        when(walletService.getWallet()).thenReturn(wallet);
        when(paymentMapper.createPaymentDtoToPayment(dto)).thenReturn(payment);
        when(constantService.getConstant(ConstantsEnum.COMMISSION_PERCENTAGE.getId()))
                .thenReturn(constant);
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(savedPayment);

        Payment result = paymentService.registerPayment(dto);

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());

        Payment captured = captor.getValue();

        assertAll(
                () -> assertEquals(wallet, captured.getWallet()),
                () -> assertEquals(REAL_AMOUNT, captured.getAmount()),
                () -> assertEquals(COMMISSION, captured.getCommission()),
                () -> assertEquals(savedPayment, result)
        );
    }

    @Test
    void getAllOwnedPayments_success() {

        // Arrange
        Wallet wallet =
                new Wallet();

        wallet.setId(WALLET_ID);

        List<Payment> payments =
                List.of(new Payment());

        List<PaymentDTO> paymentDTOS =
                List.of(new PaymentDTO());

        when(walletService.getWallet())
                .thenReturn(wallet);

        when(paymentRepository.findByWalletAndDateRange(
                eq(WALLET_ID),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(payments);

        when(paymentMapper.paymentToPaymentDto(payments))
                .thenReturn(paymentDTOS);

        // Act
        List<PaymentDTO> result =
                paymentService.getAllOwnedPayments(
                        INIT_DATE,
                        END_DATE
                );

        // Assert
        assertAll(
                () -> verify(walletService)
                        .getWallet(),

                () -> verify(paymentRepository)
                        .findByWalletAndDateRange(
                                eq(WALLET_ID),
                                any(LocalDateTime.class),
                                any(LocalDateTime.class)
                        ),

                () -> verify(paymentMapper)
                        .paymentToPaymentDto(payments),

                () -> assertEquals(paymentDTOS, result)
        );
    }

    @Test
    void getAllPayments_success() {

        // Arrange
        List<Payment> payments =
                List.of(new Payment());

        List<PaymentDTO> paymentDTOS =
                List.of(new PaymentDTO());

        when(paymentRepository.findByCreatedAtBetweenNullable(
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(payments);

        when(paymentMapper.paymentToPaymentDto(payments))
                .thenReturn(paymentDTOS);

        // Act
        List<PaymentDTO> result =
                paymentService.getAllPayments(
                        INIT_DATE,
                        END_DATE
                );

        // Assert
        assertAll(
                () -> verify(paymentRepository)
                        .findByCreatedAtBetweenNullable(
                                any(LocalDateTime.class),
                                any(LocalDateTime.class)
                        ),

                () -> verify(paymentMapper)
                        .paymentToPaymentDto(payments),

                () -> assertEquals(paymentDTOS, result)
        );
    }
}