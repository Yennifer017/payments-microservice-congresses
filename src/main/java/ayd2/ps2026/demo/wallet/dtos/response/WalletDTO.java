package ayd2.ps2026.demo.wallet.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private Integer id;
    private Integer userId;
    private Float currency;
}
