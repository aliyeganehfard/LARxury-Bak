package ir.larxury.auth.service.service;


import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.model.enums.AuthenticationOperationType;

public interface AuthenticationLogService {

    void successAttempt(User user, AuthenticationOperationType operationType);

    void failedAttempt(User user, AuthenticationOperationType operationType);
}
