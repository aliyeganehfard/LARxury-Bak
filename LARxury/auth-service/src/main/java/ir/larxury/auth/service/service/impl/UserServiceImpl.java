package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.database.model.Role;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.database.model.enums.RoleName;
import ir.larxury.auth.service.database.repository.UserRepository;
import ir.larxury.auth.service.database.repository.projection.UserIdProjection;
import ir.larxury.auth.service.database.repository.projection.UserInformationProjection;
import ir.larxury.auth.service.service.RoleService;
import ir.larxury.auth.service.service.UserService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public void save(User user) {
        userRepository.save(user);
        log.info("save new user with username {}!", user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage() + " with username {}", username);
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND_BY_EMAIL.getTechnicalMessage() + " email is {}", email);
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND_BY_EMAIL);
        });
    }

    @Override
    public UserIdProjection findUserIdByUsername(String username) {
        var userIdProjection = userRepository.findUserIdByUsername(username);
        if (userIdProjection.isEmpty()) {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage() + " for username {}", username);
            throw new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        }
        return userIdProjection.get(0);
    }

    @Override
    public UserInformationProjection findUserInformationById(String id) {
        var userInfo = userRepository.getUserInformationById(UUID.fromString(id));
        if (userInfo == null) {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND_BY_EMAIL.getTechnicalMessage() + " id is {}", id);
            throw new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        }
        return userInfo;
    }

    @Override
    public void setShopAdminRole(String id) {
        var user = findById(id);

        var checkRole = user.getRoles().stream()
                .anyMatch(role -> !RoleName.ROLE_USER.equals(role.getName()));
        if (checkRole) {
            log.error(ErrorCode.AUTH_ROLE_ACCESS_DENIED_TO_CHANGE.getTechnicalMessage() + " this user {} is not not have ROLE_USER", user.getUsername());
            throw new AuthException(ErrorCode.AUTH_ROLE_ACCESS_DENIED_TO_CHANGE);
        }

        var role = roleService.findByName(RoleName.ROLE_SHOP_ADMIN);

        user.getRoles().clear();
        user.getRoles().add(role);
        update(user);
    }

    private User findById(String uuid) {
        return userRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_USER_NOT_FOUND.getTechnicalMessage() + " with uuid {}", uuid);
            return new AuthException(ErrorCode.AUTH_USER_NOT_FOUND);
        });
    }

    private void update(User user) {
        userRepository.save(user);
        log.info("update user with id = {}", user.getId());
    }

}
