package org.delivery.account.config;

import lombok.extern.slf4j.Slf4j;
import org.delivery.account.domain.storeuser.service.StoreUserAuthorizationService;
import org.delivery.account.domain.token.business.TokenBusiness;
import org.delivery.account.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private StoreUserAuthorizationService storeUserAuthorizationService;
    private PasswordEncoder passwordEncoder;
    private Environment env;
    @Autowired
    private TokenBusiness tokenBusiness;

    private final String storeLoginUri = "/login";

    private List<String> SWAGGER=List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    public SecurityConfig(StoreUserAuthorizationService storeUserAuthorizationService, PasswordEncoder passwordEncoder, Environment env){
        this.storeUserAuthorizationService = storeUserAuthorizationService;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
    }

    @Bean
    protected SecurityFilterChain storeFilterChain(HttpSecurity httpSecurity) throws Exception{

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(storeUserAuthorizationService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
            .csrf().disable();

        httpSecurity
                .antMatcher(storeLoginUri)
                .authorizeHttpRequests((it)->it
                        .requestMatchers(new AntPathRequestMatcher(storeLoginUri)).authenticated())
                        .authenticationManager(authenticationManager);

        httpSecurity
                .formLogin(Customizer.withDefaults())
                .addFilter(getAuthenticationFilter(authenticationManager))
                .headers((it) ->it.frameOptions((ops) -> ops.sameOrigin()));

        return httpSecurity.build();
   }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception{
        return new AuthenticationFilter(authenticationManager, storeUserAuthorizationService, env, tokenBusiness);
    }
}
