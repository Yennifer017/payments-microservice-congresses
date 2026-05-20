package ayd2.ps2026.demo.wallet.repositories;

import ayd2.ps2026.demo.wallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Optional<Wallet> findByUserId(Integer userId);
}
