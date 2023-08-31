package ir.larxury.core.service.common.dto.shopPlace.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.larxury.core.service.common.dto.BaseIdDto;
import ir.larxury.core.service.database.model.enums.PlaceStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShopPlaceRes extends BaseIdDto implements Serializable {

    private String name;
    private String address;
    private PlaceStatus status;
}
