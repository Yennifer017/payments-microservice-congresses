package ayd2.ps2026.demo.wallet.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class WalletDTO {
    private Integer id;
    private Integer userId;
    private Float currency;
}
