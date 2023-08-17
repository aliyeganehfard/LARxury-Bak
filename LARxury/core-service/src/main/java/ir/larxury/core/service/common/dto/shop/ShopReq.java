package ir.larxury.core.service.common.dto.shop;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopReq implements Serializable {

    @NotEmpty(message = "نام فروشگاه نمیتواند خالی باشد")
    private String name;
}
