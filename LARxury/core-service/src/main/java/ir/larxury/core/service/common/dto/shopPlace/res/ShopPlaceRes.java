package ir.larxury.core.service.common.dto.shopPlace.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.larxury.core.service.common.dto.BaseIdDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShopPlaceRes extends BaseIdDto implements Serializable {

    @NotEmpty(message = "نام مکان فروشگاه نمیتواند خالی باشد")
    private String name;

    @NotEmpty(message = "آدرس مکان فروشگاه نمیتواند خالی باشد")
    private String address;

}
