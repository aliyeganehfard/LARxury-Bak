package ir.larxury.core.service.common.dto.product.req;

import ir.larxury.core.service.common.dto.BaseIdDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProductSaveReq {

    @NotEmpty(message = "نام محصول نمیتواند خالی باشد")
    @Pattern(regexp = "^(?=.{6,})\\b\\w{2,}(?:\\s+\\w{2,})+$", message = "نام محصول باید حداقل شامل ۲ کلمه و ۶ حرف باشد")
    private String name;

    @NotEmpty(message = "ویژگی های محصول نمیتواند خالی باشد")
    private String attribute;

    private String description;

    @Min(value = 100_000, message = "قیمت باید بیشتر از 10 هزار تومان باشد")
    @NotNull(message = "قیمت محصول نمیتواند خالی باشد")
    private Long price;

    @NotNull(message = "فروشگاهی برای محصول انتخاب نشده است")
    private BaseIdDto shop;

    @NotNull(message = "دسته بندی برای محصول انتخاب نشده است")
    private BaseIdDto category;
}
