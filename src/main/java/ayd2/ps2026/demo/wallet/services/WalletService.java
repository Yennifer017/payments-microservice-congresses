package ayd2.ps2026.demo.wallet.services;

import ayd2.ps2026.demo.wallet.dtos.request.AddAmountDTO;
import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import ayd2.ps2026.demo.wallet.models.Wallet;

public interface WalletService {
    Wallet createWallet(Integer userId);
    Wallet getWallet();
    WalletDTO addToWallet(AddAmountDTO addAmountDTO);
}
