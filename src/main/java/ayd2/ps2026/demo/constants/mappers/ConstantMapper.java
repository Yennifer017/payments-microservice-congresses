package ayd2.ps2026.demo.constants.mappers;

import ayd2.ps2026.demo.constants.dtos.request.EditConstantDTO;
import ayd2.ps2026.demo.constants.dtos.response.ConstantDTO;
import ayd2.ps2026.demo.constants.models.Constant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ConstantMapper {
    ConstantDTO constantToConstantDto(Constant constant);
    List<ConstantDTO> constantToConstantDto(List<Constant> constants);

    /** update parcial
     *
     * @param dto
     * @param entity
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConstantFromDto(EditConstantDTO dto, @MappingTarget Constant entity);

}
