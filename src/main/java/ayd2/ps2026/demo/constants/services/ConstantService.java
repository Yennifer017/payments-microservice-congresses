package ayd2.ps2026.demo.constants.services;

import ayd2.ps2026.demo.common.exceptions.NotFoundException;
import ayd2.ps2026.demo.constants.dtos.request.EditConstantDTO;
import ayd2.ps2026.demo.constants.dtos.response.ConstantDTO;
import ayd2.ps2026.demo.constants.models.Constant;
import org.apache.tomcat.util.bcel.Const;

import java.util.List;

public interface ConstantService {
    ConstantDTO updateConstant(EditConstantDTO editConstantDTO) throws NotFoundException;
    Constant getConstant(Integer id) throws NotFoundException;
    List<ConstantDTO> getAllConstants();
}
