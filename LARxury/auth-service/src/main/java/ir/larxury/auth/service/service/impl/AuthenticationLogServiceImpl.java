package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.database.model.AuthenticationLog;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.model.enums.AuthenticationOperationType;
import ir.larxury.auth.service.database.model.enums.AuthenticationStatus;
import ir.larxury.auth.service.database.repository.AuthenticationLogRepository;
import ir.larxury.auth.service.service.AuthenticationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationLogServiceImpl implements AuthenticationLogService {

    @Autowired
    private AuthenticationLogRepository authenticationLogRepository;

    @Override
    public void successAttempt(User user, AuthenticationOperationType operationType) {
        var authLog = AuthenticationLog.builder()
                .status(AuthenticationStatus.SUCCESSFUL)
                .operationType(operationType)
                .user(user)
                .build();
        save(authLog);
        log.info("signIn for user {} was successful!",user.getUsername());
    }

    @Override
    public void failedAttempt(User user,AuthenticationOperationType operationType) {
        var authLog = AuthenticationLog.builder()
                .status(AuthenticationStatus.FAILED)
                .operationType(operationType)
                .user(user)
                .build();
        save(authLog);
        log.info("signIn for user {} was failed!",user.getUsername());
    }

    private void save(AuthenticationLog authLog){
        authenticationLogRepository.save(authLog);
    }
}
