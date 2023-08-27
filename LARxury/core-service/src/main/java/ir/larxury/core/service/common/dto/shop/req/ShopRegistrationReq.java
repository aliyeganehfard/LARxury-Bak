package ir.larxury.core.service.common.dto.shop.req;

import ir.larxury.core.service.common.dto.BaseReqDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShopRegistrationReq implements Serializable {

    @NotEmpty(message = "نام فروشگاه نمیتواند خالی باشد")
    private String name;

    @NotEmpty(message = "آدرس فروشگاه نمیتواند خالی باشد")
    private String address;

    @NotEmpty(message = " توضیحات نمیتواند خالی باشد")
    private String description;

    @NotEmpty(message = "کتگوری نمیتواند خالی باشد")
    private List<BaseReqDto> categories;
}
