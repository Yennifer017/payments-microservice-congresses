package ayd2.ps2026.demo.auth.users.models;

import ayd2.ps2026.demo.auth.users.enums.RolesEnum;
import ayd2.ps2026.demo.common.models.entities.Auditor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
public class AppUser{

    private Integer id;

    private String username;

    private String role;

}
