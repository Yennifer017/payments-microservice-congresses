package ayd2.ps2026.demo.wallet.services;

import ayd2.ps2026.demo.auth.jwt.CustomUserDetails;
import ayd2.ps2026.demo.wallet.dtos.request.AddAmountDTO;
import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import ayd2.ps2026.demo.wallet.mappers.WalletMapper;
import ayd2.ps2026.demo.wallet.models.Wallet;
import ayd2.ps2026.demo.wallet.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public Wallet createWallet(Integer userId) {
        Wallet wallet = new Wallet(userId, 0f);
        return this.walletRepository.save(wallet);
    }

    @Override
    public Wallet getWallet() {
        Integer userId =
                ((CustomUserDetails)
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal())
                        .getId();
        return this.walletRepository.findByUserId(userId)
                .orElseGet(() -> this.createWallet(userId));
    }

    @Override
    public WalletDTO addToWallet(AddAmountDTO addAmountDTO) {
        Wallet wallet = this.getWallet();
        wallet.setCurrency(wallet.getCurrency() + addAmountDTO.getAmount());
        this.walletRepository.save(wallet);
        return walletMapper.walletToWalletDto(wallet);
    }


}
