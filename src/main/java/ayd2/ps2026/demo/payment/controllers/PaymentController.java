package ayd2.ps2026.demo.payment.controllers;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.payment.dtos.request.ModelCreateDTO;
import ayd2.ps2026.demo.payment.mappers.PaymentMapper;
import ayd2.ps2026.demo.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/example")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    /**
     * example
     */
    @Operation(summary = "example",
            description = "example",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exito"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @PostMapping("/example")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void example(@RequestBody @Valid ModelCreateDTO modelCreateDTO) {

    }


    /**
     * example
     **/
    @Operation(summary = "example",
            description = "example",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exitoso"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @GetMapping("/{example}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void example(@PathVariable String example) throws NotFoundException {

    }

}
