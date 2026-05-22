package ayd2.ps2026.demo.constants.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.constants.dtos.request.EditConstantDTO;
import ayd2.ps2026.demo.constants.dtos.response.ConstantDTO;
import ayd2.ps2026.demo.constants.mappers.ConstantMapper;
import ayd2.ps2026.demo.constants.models.Constant;
import ayd2.ps2026.demo.constants.repositories.ConstantRepository;
import ayd2.ps2026.demo.payment.mappers.PaymentMapper;
import ayd2.ps2026.demo.payment.repositories.PaymentRepository;
import ayd2.ps2026.demo.payment.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ConstantServiceImpl implements ConstantService {

    private final ConstantRepository constantRepository;
    private final ConstantMapper constantMapper;

    @Override
    public ConstantDTO updateConstant(EditConstantDTO editConstantDTO) throws NotFoundException {
        Constant constant = this.getConstant(editConstantDTO.getId());
        constantMapper.updateConstantFromDto(editConstantDTO, constant);
        return this.constantMapper.constantToConstantDto(constantRepository.save(constant));
    }

    @Override
    public Constant getConstant(Integer id) throws NotFoundException {
        return this.constantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro una constante"));
    }

    @Override
    public List<ConstantDTO> getAllConstants() {
        List<Constant> constants = this.constantRepository.findAll();
        return this.constantMapper.constantToConstantDto(constants);
    }

}
