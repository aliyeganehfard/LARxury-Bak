package ir.larxury.core.service.common.dto.shop.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.larxury.core.service.common.dto.category.res.CategoryCommonRes;
import ir.larxury.core.service.common.dto.shopPlace.res.ShopPlaceRes;
import ir.larxury.core.service.database.model.Category;
import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.database.model.enums.ShopStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShopAwaitingConfigurationRes implements Serializable {

    private Long id;
    private Date createDate;
    private String name;
    private ShopStatus shopStatus;
    private String address;
    private String about;
    private String userId;
    private List<CategoryCommonRes> categories;
    private ShopPlaceRes place;
}
