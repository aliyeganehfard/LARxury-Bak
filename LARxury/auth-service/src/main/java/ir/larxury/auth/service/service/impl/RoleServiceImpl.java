package ir.larxury.auth.service.service.impl;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.database.model.Role;
import ir.larxury.auth.service.database.model.enums.RoleName;
import ir.larxury.auth.service.database.repository.RoleRepository;
import ir.larxury.auth.service.service.RoleService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> {
            log.error(ErrorCode.AUTH_ROLE_NOT_FOUND.getTechnicalMessage() + " with role {}", roleName);
            return new AuthException(ErrorCode.AUTH_ROLE_NOT_FOUND);
        });
    }
}
