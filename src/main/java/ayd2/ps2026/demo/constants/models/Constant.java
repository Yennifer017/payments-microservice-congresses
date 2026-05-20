package ayd2.ps2026.demo.constants.models;

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
@Table(name = "constants")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Constant extends Auditor {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float value;

}
