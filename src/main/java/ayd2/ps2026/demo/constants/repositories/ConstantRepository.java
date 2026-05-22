package ayd2.ps2026.demo.constants.repositories;

import ayd2.ps2026.demo.constants.models.Constant;
import ayd2.ps2026.demo.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstantRepository extends JpaRepository<Constant, Integer> {
}
