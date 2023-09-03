package ir.larxury.common.utils.common.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(0, "internal server error!", "خطای درونی سرور"),
    SUCCESSFUL(1, "operation was successful!", "عملیات با موفقیت انجام شد"),
    METHOD_ARGUMENT_NOT_VALID(2,"request method argument not valid!","داده ورودی صحیص نمی باشد"),
    UNKNOWN_ERROR(3,"unknown error!","خطای ناشناسی رخ داده"),
    RSA_TROUBLE_READ_PUBLIC_KEY(10, "trouble in read public key file!", "در خواندن کلید عمومی مشکلی پیش آمده"),
    RSA_TROUBLE_READ_PRIVATE_KEY(11,"trouble in read private key file!","در خواندن کلید خصوصی مشکلی پیش آمده"),
    TOKEN_EXPIRED(20, "token has expired earlier!", "زمان توکن منقضی شده"),
    TOKEN_INVALID(21,"invalid token!","توکن نامعتر است"),
    TOKEN_VERIFICATION_UNKNOWN_ERROR(22,"an unknown error occurred in token validation","خطای ناشناسی در اعتبار سنجی توکن پیش آمده"),
    TOKEN_IS_MISSING(23,"token is missing","توکن پیدا نشد"),
    REFRESH_TOKEN_IS_MISSING(24,"refresh token is missing","توکن بازیابی پیدا نشد"),
    AUTH_USER_NOT_FOUND(30,"user not found!","کاربر مورد نظر پیدا نشد"),
    AUTH_USER_NOT_FOUND_BY_PHONE_NUMBER(31,"user with this phone number was not found!","کاربر با این شماره تماس پیدا نشد"),
    AUTH_USER_NOT_FOUND_BY_EMAIL(32,"user with this email was not found!","کاربر با این ایمیل پیدا نشد"),
    AUTH_PASSWORD_CONFIRMATION_MISMATCH(33,"password and verification password do not match!","رمز عبور و رمز عبور تأیید مطابقت ندارند"),
    AUTH_INCORRECT_PASSWORD(34,"password is incorrect!","رمز عبور نادرست است"),
    AUTH_OTP_MISMATCH(35,"entered OTP do not match!","عدم مطابقت کد تایید"),
    AUTH_OTP_EXPIRED(36,"OTP has expired earlier!","کد تایید زودتر منقضی شده است"),
    AUTH_OTP_IS_ALREADY_USED(37,"OTP is already used!","قبلا از کد تایید استفاده شده است"),
    AUTH_OTP_NOT_FOUND_BY_PHONE_NUMBER(38,"OTP with this phone number was not found!","هیچ کد تاییدی با این شماره تماس پیدا نشد"),
    AUTH_ROLE_NOT_FOUND(40,"role not found!","نقش مورد نظر پیدا نشد"),
    AUTH_ROLE_ACCESS_DENIED_TO_CHANGE(41,"you can only change the users role!","شما تنها می تواند نقش کاربر ها را تغییر دهید"),
    AUTH_INTERNAL_ERROR_IN_SENDING_OTP(50,"there is a internal error in sending the verification code!","خطای درونی در ارسال کد تایید پیش امده"),
    AUTH_TROUBLE_TO_SEND_OTP(51,"there is a problem sending the verification code!","مشکلی در ارسال کد تایید پیش امده"),


    CORE_SERVICE_CONNECTION_ERROR(1200,"trouble to send http request!","مشکلی در برقراری ارتباط با سرویس های دیگر رخ داده"),
    CORE_SERVICE_UNSUCCESSFUL_REQUEST(1201,"request is unsuccessful!","درخواست ناموفق بود"),
    CORE_SERVICE_TROUBLE_TO_PARS_DATA(1202,"trouble to parse incoming data from other service!","مشکل در تجزیه داده های دریافتی از سرویس های دیگر"),
    CORE_SERVICE_DUPLICATE_SHOP_NAME(1203,"shop with this name already exists!","فروشگاهی با این نام در حال حاضر وجود دارد"),
    CORE_SERVICE_SHOP_NOT_FOUND(1204,"shop not found!","فروشگاهی با این نام پیدا نشد"),
    CORE_SERVICE_SHOP_EARLIER_APPROVED(1205,"shop earlier approved!","فروشگاه از قبل تایید شده است"),
    CORE_SERVICE_SHOP_TROUBLE_TO_REJECT(1206,"just shop with awaiting confirmation status can reject!","تنها فروشگاه هایی با وضعیت 'در انتظار تایید' امکان رد/نپدیرفته شدن دارند"),
    CORE_SERVICE_TROUBLE_TO_SEND_INSTANT_DELIVERY(1207,"there is a problem sending the instant delivery message to dispatcher service code!","مشکلی در ارسال پیام به وجود آمده"),
    CORE_SERVICE_CATEGORY_NOT_FOUND(1208,"category not found!","دسته بندی با این شناسه پیدا نشد"),
    CORE_SERVICE_CATEGORY_ID_NOT_UNIQUE(1209,"category ids must be unique!","نمیتوان دسته بندی تکراری انتخاب کرد"),
    CORE_SERVICE_CATEGORY_BAD_CATEGORY_IDS(1210,"one of the category IDs is incorrect!","یکی از شناسه دسته نادرست است"),
    CORE_SERVICE_CATEGORY_IS_NUT_SUBCATEGORY(1211,"No subcategories were found for this category!","هیچ زیرمجموعه ای برای این دسته بندی پیدا نشد"),


    CORE_SERVICE_SHOP_PLACE_NOT_FOUND(1220,"shop place not found!","مکان فروشگاه با این شناسه پیدا نشد"),
    CORE_SERVICE_SHOP_PLACE_UNKNOWN_ERROR(1221,"unknown error to find shopPlace with unknown status!","خطای ناشناخته برای یافتن مکان فروشگاه با وضعیت ناشناخته پیش آمده"),

    CORE_SERVICE_PRODUCT_NOT_FOUND(1230,"product not found!","محصول پیدا نشد"),

    CORE_SERVICE_COMMENT_NOT_FOUND(1240,"comment not found!","کامنت پیدا نشد"),

    CORE_SERVICE_TROUBLE_TO_SAVE_PRODUCT_VIEWS(1250,"trouble to save product views!","مشکلی در دخیره بازدید های محصولات پیش آمده است"),


    DISPATCHER_TROUBLE_IN_INSTANT_DELIVERY(1500,"trouble to send notification in instant delivery!","خظایی در ارسال اعلان لحظه ای رخ داده است"),
    DISPATCHER_TROUBLE_IN_SEND_OTP(1501,"trouble to send otp!","مشکلی در ارسال کد تایید رخ داده است");

    private final Integer code;
    private final String technicalMessage;
    private final String message;

    public static ErrorCode getTechnicalMessageByCode(Integer code){
        for (ErrorCode value : ErrorCode.values()) {
            if (Objects.equals(value.getCode(), code))
                return value;
        }
        return ErrorCode.UNKNOWN_ERROR;
    }

}
