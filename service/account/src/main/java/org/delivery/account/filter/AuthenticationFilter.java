package org.delivery.account.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.account.domain.storeuser.model.StoreUserLoginRequest;
import org.delivery.account.domain.storeuser.model.UserSession;
import org.delivery.account.domain.storeuser.service.StoreUserAuthorizationService;
import org.delivery.account.domain.storeuser.service.StoreUserService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final StoreUserService storeUserService;
    private final StoreUserAuthorizationService storeUserAuthorizationService;
    private Environment env;
    private PasswordEncoder passwordEncoder;
    public AuthenticationFilter(AuthenticationManager authenticationManager, StoreUserAuthorizationService storeUserAuthorizationService, Environment env,StoreUserService storeUserService){
        super(authenticationManager);
        this.storeUserAuthorizationService = storeUserAuthorizationService;
        this.env = env;
        this.storeUserService = storeUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            StoreUserLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),StoreUserLoginRequest.class);
            var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword(),new ArrayList<>());
            log.info("account token: {}", authenticationToken);
            return getAuthenticationManager().authenticate(authenticationToken);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        var userId = ((UserSession)authResult.getPrincipal()).getUserId();
    }
}
