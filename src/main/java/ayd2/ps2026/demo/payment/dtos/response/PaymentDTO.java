package ayd2.ps2026.demo.payment.dtos.response;

import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PaymentDTO {

    private WalletDTO wallet;
    private Integer congressId;
    private String description;
    private Float amount;
    private Float commission;

}
