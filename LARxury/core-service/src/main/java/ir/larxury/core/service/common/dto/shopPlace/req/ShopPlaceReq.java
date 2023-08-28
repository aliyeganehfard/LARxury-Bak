package ir.larxury.core.service.common.dto.shopPlace.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopPlaceReq implements Serializable {

    @NotEmpty(message = "نام مکان فروشگاه نمیتواند خالی باشد")
    private String name;

    @NotEmpty(message = "آدرس مکان فروشگاه نمیتواند خالی باشد")
    private String address;

}
