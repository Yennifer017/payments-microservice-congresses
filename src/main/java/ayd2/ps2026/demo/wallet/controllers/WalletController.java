package ayd2.ps2026.demo.wallet.controllers;

import ayd2.ps2026.demo.wallet.dtos.request.AddAmountDTO;
import ayd2.ps2026.demo.wallet.dtos.response.WalletDTO;
import ayd2.ps2026.demo.wallet.mappers.WalletMapper;
import ayd2.ps2026.demo.wallet.services.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final WalletMapper walletMapper;

    /**
     * Add currency to owned wallet
     */
    @Operation(summary = "Add currency to owned wallet",
            description = "Add currency to owned wallet",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exito"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @PostMapping("/add-currency")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public WalletDTO example(@RequestBody @Valid AddAmountDTO addAmountDTO) {
        return walletService.addToWallet(addAmountDTO);
    }


    /**
     * gets the user's current wallet if not exists adds one
     **/
    @Operation(summary = "gets the user's current wallet if not exists adds one",
            description = "gets the user's current wallet if not exists adds one",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exitoso"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public WalletDTO getMyWallet() {
        return walletMapper.walletToWalletDto(walletService.getWallet());
    }

}
