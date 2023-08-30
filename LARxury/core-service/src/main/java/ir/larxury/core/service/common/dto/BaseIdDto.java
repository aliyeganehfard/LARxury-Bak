package ir.larxury.core.service.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseIdDto implements Serializable {

    @Pattern(regexp = "^[1-9][0-9]*$")
    @NotNull(message = "شناسه نمیتواند خالی باشد")
    private Long id;
}
