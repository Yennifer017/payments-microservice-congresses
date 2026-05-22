package ayd2.ps2026.demo.payment.models;

import ayd2.ps2026.demo.common.models.entities.Auditor;
import ayd2.ps2026.demo.wallet.models.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends Auditor {

    @Column(nullable = false)
    private Integer congressId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private Float commission;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Wallet wallet;

}
