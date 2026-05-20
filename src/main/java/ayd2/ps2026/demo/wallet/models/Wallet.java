package ayd2.ps2026.demo.wallet.models;

import ayd2.ps2026.demo.common.models.entities.Auditor;
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
public class Wallet extends Auditor {

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Float currency;

}
