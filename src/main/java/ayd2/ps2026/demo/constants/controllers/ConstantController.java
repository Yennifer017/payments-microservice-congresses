package ayd2.ps2026.demo.constants.controllers;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.constants.dtos.request.EditConstantDTO;
import ayd2.ps2026.demo.constants.dtos.response.ConstantDTO;
import ayd2.ps2026.demo.constants.mappers.ConstantMapper;
import ayd2.ps2026.demo.constants.services.ConstantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/constants")
@RequiredArgsConstructor
public class ConstantController {
    private final ConstantService constantService;
    private final ConstantMapper constantMapper;

    /**
     * update a constant
     */
    @Operation(summary = "update a constant",
            description = "update a constant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exito"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ConstantDTO updateConstant(@RequestBody @Valid EditConstantDTO editConstantDTO) throws NotFoundException {
        return constantService.updateConstant(editConstantDTO);
    }


    /**
     * get all constants
     **/
    @Operation(summary = "get all constants",
            description = "get all constants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Exitoso"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConstantDTO> getAllConstants(@PathVariable String example) throws NotFoundException {
        return this.constantService.getAllConstants();
    }

}
