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
    AUTH_PASSWORD_CONFIRMATION_MISMATCH(32,"password and verification password do not match!","رمز عبور و رمز عبور تأیید مطابقت ندارند"),
    AUTH_INCORRECT_PASSWORD(33,"password is incorrect!","رمز عبور نادرست است"),
    AUTH_OTP_MISMATCH(34,"entered OTP do not match!","عدم مطابقت کد تایید"),
    AUTH_OTP_EXPIRED(35,"OTP has expired earlier!","کد تایید زودتر منقضی شده است"),
    AUTH_OTP_IS_ALREADY_USED(36,"OTP is already used!","قبلا از کد تایید استفاده شده است"),
    AUTH_OTP_NOT_FOUND_BY_PHONE_NUMBER(37,"OTP with this phone number was not found!","هیچ کد تاییدی با این شماره تماس پیدا نشد"),
    AUTH_ROLE_NOT_FOUND(40,"role not found!","نقش مورد نظر پیدا نشد"),
    AUTH_ROLE_ACCESS_DENIED_TO_CHANGE(40,"you can only change the users role!","شما تنها می تواند نقش کاربر ها را تغییر دهید"),
    CORE_SERVICE_CONNECTION_ERROR(1200,"trouble to send http request!","مشکلی در برقراری ارتباط با سرویس های دیگر رخ داده"),
    CORE_SERVICE_UNSUCCESSFUL_REQUEST(1201,"request is unsuccessful!","درخواست ناموفق بود"),
    CORE_SERVICE_TROUBLE_TO_PARS_DATA(1202,"trouble to parse incoming data from other service!","مشکل در تجزیه داده های دریافتی از سرویس های دیگر"),
    CORE_SERVICE_DUPLICATE_SHOP_NAME(1203,"shop with this name already exists!","فروشگاهی با این نام در حال حاضر وجود دارد"),
    CORE_SERVICE_SHOP_NOT_FOUND(1204,"shop not found!","فروشگاهی با این نام پیدا نشد");

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
