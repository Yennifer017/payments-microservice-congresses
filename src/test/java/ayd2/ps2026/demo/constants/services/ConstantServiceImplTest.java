package ayd2.ps2026.demo.constants.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.constants.dtos.request.EditConstantDTO;
import ayd2.ps2026.demo.constants.dtos.response.ConstantDTO;
import ayd2.ps2026.demo.constants.mappers.ConstantMapper;
import ayd2.ps2026.demo.constants.models.Constant;
import ayd2.ps2026.demo.constants.repositories.ConstantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConstantServiceImplTest {

    private static final Integer CONSTANT_ID = 1;

    @Mock
    private ConstantRepository constantRepository;

    @Mock
    private ConstantMapper constantMapper;

    @InjectMocks
    private ConstantServiceImpl constantService;

    @Test
    void updateConstant_success() throws NotFoundException {

        // Arrange
        EditConstantDTO editConstantDTO = new EditConstantDTO();
        editConstantDTO.setId(CONSTANT_ID);

        Constant constant = new Constant();
        constant.setId(CONSTANT_ID);

        ConstantDTO constantDTO = new ConstantDTO();
        constantDTO.setId(CONSTANT_ID);

        when(constantRepository.findById(CONSTANT_ID))
                .thenReturn(Optional.of(constant));

        when(constantRepository.save(constant))
                .thenReturn(constant);

        when(constantMapper.constantToConstantDto(constant))
                .thenReturn(constantDTO);

        // Act
        ConstantDTO result =
                constantService.updateConstant(editConstantDTO);

        // Assert
        assertAll(
                () -> verify(constantRepository)
                        .findById(CONSTANT_ID),

                () -> verify(constantMapper)
                        .updateConstantFromDto(
                                editConstantDTO,
                                constant
                        ),

                () -> verify(constantRepository)
                        .save(constant),

                () -> verify(constantMapper)
                        .constantToConstantDto(constant),

                () -> assertEquals(constantDTO, result)
        );
    }

    @Test
    void updateConstant_notFound_throwsNotFoundException() {

        // Arrange
        EditConstantDTO editConstantDTO = new EditConstantDTO();
        editConstantDTO.setId(CONSTANT_ID);

        when(constantRepository.findById(CONSTANT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                NotFoundException.class,
                () -> constantService.updateConstant(editConstantDTO)
        );

        verify(constantRepository, never()).save(any());
    }

    @Test
    void getConstant_success() throws NotFoundException {

        // Arrange
        Constant constant = new Constant();
        constant.setId(CONSTANT_ID);

        when(constantRepository.findById(CONSTANT_ID))
                .thenReturn(Optional.of(constant));

        // Act
        Constant result = constantService.getConstant(CONSTANT_ID);

        // Assert
        assertAll(
                () -> verify(constantRepository)
                        .findById(CONSTANT_ID),

                () -> assertEquals(constant, result)
        );
    }

    @Test
    void getConstant_notFound_throwsNotFoundException() {

        // Arrange
        when(constantRepository.findById(CONSTANT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                NotFoundException.class,
                () -> constantService.getConstant(CONSTANT_ID)
        );
    }

    @Test
    void getAllConstants_success() {

        // Arrange
        List<Constant> constants =
                List.of(new Constant());

        List<ConstantDTO> constantDTOS =
                List.of(new ConstantDTO());

        when(constantRepository.findAll())
                .thenReturn(constants);

        when(constantMapper.constantToConstantDto(constants))
                .thenReturn(constantDTOS);

        // Act
        List<ConstantDTO> result =
                constantService.getAllConstants();

        // Assert
        assertAll(
                () -> verify(constantRepository)
                        .findAll(),

                () -> verify(constantMapper)
                        .constantToConstantDto(constants),

                () -> assertEquals(constantDTOS, result)
        );
    }
}