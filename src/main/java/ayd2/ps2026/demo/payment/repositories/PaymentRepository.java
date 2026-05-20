package ayd2.ps2026.demo.payment.repositories;

import ayd2.ps2026.demo.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("""
        SELECT p
        FROM Payment p
        WHERE (:start IS NULL OR p.createdAt >= :start)
          AND (:end IS NULL OR p.createdAt <= :end)
    """)
    List<Payment> findByCreatedAtBetweenNullable(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT p
        FROM Payment p
        WHERE p.wallet.id = :walletId
          AND (:start IS NULL OR p.createdAt >= :start)
          AND (:end IS NULL OR p.createdAt <= :end)
    """)
    List<Payment> findByWalletAndDateRange(
            @Param("walletId") Integer walletId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
