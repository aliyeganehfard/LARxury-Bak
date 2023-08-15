package ir.larxury.common.utils.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    public void setCurrentUser(UsernamePasswordAuthenticationToken authenticationToken) {
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}
