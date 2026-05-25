package ayd2.ps2026.demo.payment.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentDTO {

    @NotNull
    @PositiveOrZero
    private Integer congressId;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @PositiveOrZero
    private Float amount;


}
