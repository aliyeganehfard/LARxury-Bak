package ir.larxury.core.service.common.dto.category.req;

import ir.larxury.core.service.common.dto.BaseReqDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubCategoryReq implements Serializable {

    @NotEmpty(message = "نام دسته بندی نمیتواند خالی باشد")
    private String title;

    @NotEmpty(message = "شناسه دسته بندی پدر وارد نشده است")
    private BaseReqDto parentCategory;
}
