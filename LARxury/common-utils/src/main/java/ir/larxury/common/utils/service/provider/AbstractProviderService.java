package ir.larxury.common.utils.service.provider;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.CommonUtilsException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.common.utils.service.JWTVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public abstract class AbstractProviderService {

    @Autowired
    private JWTVerificationService jwtVerificationService;

    private String token;

    private final ModelMapper mapper = new ModelMapper();

    public String getToken() {
        if (Objects.isNull(token) || jwtVerificationService.isJWTExpired(token)) {
            try {
                token = login();
            } catch (Exception e) {
                token = login();
            }
        }
        return token;
    }

    protected abstract String login();

    public <T> T validateResponse(GeneralResponse response, Class<T> tClass) {
        if (Boolean.TRUE.equals(response.getIsSuccess())) {
            if (response.getResultData() != null) {
                try {
                    return mapper.map(response.getResultData(), tClass);
                } catch (Exception e) {
                    log.error(ErrorCode.CORE_SERVICE_TROUBLE_TO_PARS_DATA.getTechnicalMessage());
                    throw new CommonUtilsException(ErrorCode.CORE_SERVICE_TROUBLE_TO_PARS_DATA, e);
                }
            } else {
                return null;
            }
        } else {
            log.error(ErrorCode.getTechnicalMessageByCode(response.getRsCode()).getTechnicalMessage());
            throw new CommonUtilsException(ErrorCode.getTechnicalMessageByCode(response.getRsCode()));
        }
    }

    public void validateResponse(GeneralResponse response) {
        if (Boolean.FALSE.equals(response.getIsSuccess())) {
            log.error(ErrorCode.getTechnicalMessageByCode(response.getRsCode()).getTechnicalMessage());
            throw new CommonUtilsException(ErrorCode.getTechnicalMessageByCode(response.getRsCode()));
        }
    }
}
