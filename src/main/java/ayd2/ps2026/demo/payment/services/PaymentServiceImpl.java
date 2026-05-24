package ayd2.ps2026.demo.payment.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.common.utils.MoneyUtil;
import ayd2.ps2026.demo.common.utils.TimeUtil;
import ayd2.ps2026.demo.constants.enums.ConstantsEnum;
import ayd2.ps2026.demo.constants.models.Constant;
import ayd2.ps2026.demo.constants.services.ConstantService;
import ayd2.ps2026.demo.payment.dtos.request.CreatePaymentDTO;
import ayd2.ps2026.demo.payment.dtos.response.PaymentDTO;
import ayd2.ps2026.demo.payment.mappers.PaymentMapper;
import ayd2.ps2026.demo.payment.models.Payment;
import ayd2.ps2026.demo.payment.repositories.PaymentRepository;
import ayd2.ps2026.demo.wallet.dtos.request.AddAmountDTO;
import ayd2.ps2026.demo.wallet.models.Wallet;
import ayd2.ps2026.demo.wallet.repositories.WalletRepository;
import ayd2.ps2026.demo.wallet.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
     private final WalletRepository walletRepository;
    private final PaymentMapper paymentMapper;

    private final WalletService walletService;
    private final ConstantService constantService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment registerPayment(CreatePaymentDTO createPaymentDTO) throws NotFoundException {

        Wallet wallet = walletService.getWallet();

        Constant constant = constantService.getConstant(
                ConstantsEnum.COMMISSION_PERCENTAGE.getId());

        Float discount = MoneyUtil.roundTwoDecimals(
                (double) (createPaymentDTO.getAmount() * constant.getValue()));

        Float realAmount = createPaymentDTO.getAmount() - discount;

        if (wallet.getCurrency() < realAmount) {
            throw new RuntimeException("Fondos insuficientes");
        }

        wallet.setCurrency(wallet.getCurrency() - realAmount);
        walletRepository.save(wallet);

        Payment payment = paymentMapper.createPaymentDtoToPayment(createPaymentDTO);
        payment.setWallet(wallet);
        payment.setAmount(realAmount);
        payment.setCommission(discount);

        return paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDTO> getAllOwnedPayments(LocalDate init, LocalDate end) {
        Wallet wallet = walletService.getWallet();
        List<Payment> payments = paymentRepository.findByWalletAndDateRange(
                wallet.getId(),
                TimeUtil.convertToLocalDateTime(init, true),
                TimeUtil.convertToLocalDateTime(end, false));
        return this.paymentMapper.paymentToPaymentDto(payments);
    }

    @Override
    public List<PaymentDTO> getAllPayments(LocalDate init, LocalDate end) {
        List<Payment> payments = paymentRepository.findByCreatedAtBetweenNullable(
                TimeUtil.convertToLocalDateTime(init, true),
                TimeUtil.convertToLocalDateTime(end, false));
        return this.paymentMapper.paymentToPaymentDto(payments);
    }

}
