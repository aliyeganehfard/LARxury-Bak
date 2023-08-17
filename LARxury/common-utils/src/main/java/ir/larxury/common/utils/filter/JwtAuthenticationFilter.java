package ir.larxury.common.utils.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.CommonUtilsException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.common.utils.service.JWTVerificationProvider;
import ir.larxury.common.utils.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ir.larxury.common.utils.service.JWTVerificationProvider.CLAIM_ROLES;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTVerificationProvider JWTVerificationProvider;

    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/auth/signIn") || request.getServletPath().equals("/auth/signUp") ||
                    request.getServletPath().equals("/auth/token/refresh")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request,response);
                return;
            }

            var token = authHeader.substring("Bearer ".length());
            DecodedJWT decodedJWT = JWTVerificationProvider.getDecodedJWT(token);
            var username = decodedJWT.getSubject();
            var roles = decodedJWT.getClaim(CLAIM_ROLES).asArray(String.class);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role))
            );
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            userSecurityService.setCurrentUser(authToken);

            filterChain.doFilter(request, response);

        } catch (CommonUtilsException commonUtilsException) {
            var res = GeneralResponse.unsuccessfulResponse(commonUtilsException.getErrorCode());
            setResponse(response, res);
        } catch (Exception e) {
            var res = GeneralResponse.unsuccessfulResponse(ErrorCode.INTERNAL_SERVER_ERROR);
            setResponse(response, res);
        }
    }

    private void setResponse(HttpServletResponse response, GeneralResponse res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(res));
    }
}
