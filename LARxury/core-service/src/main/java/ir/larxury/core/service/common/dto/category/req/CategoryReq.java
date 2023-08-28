package ir.larxury.core.service.common.dto.category.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryReq implements Serializable {

    @NotEmpty(message = "نام دسته بندی نمیتواند خالی باشد")
    private String title;
}
