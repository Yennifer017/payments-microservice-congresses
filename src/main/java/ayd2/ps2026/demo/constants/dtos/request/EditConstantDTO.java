package ayd2.ps2026.demo.constants.dtos.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

@Data
@Getter
public class EditConstantDTO {

    @NotNull
    @PositiveOrZero
    private Integer id;

    private String name;

    @PositiveOrZero
    private Float value;
}
