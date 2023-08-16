package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.repository.UserRepository;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;
import ir.larxury.auth.service.service.UserService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
        log.info("save new user with username {}!", user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage());
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        });
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND_BY_PHONE_NUMBER.getTechnicalMessage() + " phone number is {}", phoneNumber);
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND_BY_PHONE_NUMBER);
        });
    }

    @Override
    public UserIdProjection findUserIdByUsername(String username) {
        var userIdProjection = userRepository.findUserIdByUsername(username);
        if (userIdProjection.isEmpty()){
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage() + " for username {}", username);
            throw new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        }
        return userIdProjection.get(0);
    }

}
