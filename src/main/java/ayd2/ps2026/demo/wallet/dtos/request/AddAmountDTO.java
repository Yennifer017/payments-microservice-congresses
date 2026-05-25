package ayd2.ps2026.demo.wallet.dtos.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAmountDTO {
    @Positive
    private Float amount;
}
