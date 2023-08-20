package ir.larxury.core.service.database.model.enums;

import lombok.Getter;

@Getter
public enum ShopStatus {
    AWAITING_CONFIRMATION(0, "در انتظار تایید"),
    REJECTION(1, "تایید نشد"),
    BLOCKED(2, "مسدود شده"),
    CONFIRM(3, "تایید شده"),
    NEW(4, "جدید"),
    ACTIVE(5, "فعال");

    private final Integer index;
    private final String description;

    ShopStatus(Integer index, String description) {
        this.index = index;
        this.description = description;
    }
}
