package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {

    //swagger 주소는 인증에서 제외
    private List<String> SWAGGER=List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    //이전 방법: 상속 받기, 현재 방법: bin 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .csrf().disable() // default: 활성화, 즉 crsf 공격에 대해서 disable
            .authorizeHttpRequests(it->{
                it
                    .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations() //static 리소스에 대해서
                    ).permitAll() //모든 요청을 허가

                    //swagger 주소는 인증없이 허가
                    .mvcMatchers( //제외할 주소를 패턴으로 입력
                        SWAGGER.toArray(new String[0])
                    ).permitAll()

                    .mvcMatchers(
                        "/open-api/**" //open-api 하위에 있는 주소에 대해 모든 요청을 허가
                    ).permitAll()

                    .anyRequest().authenticated();//그 외 모든 주소는 인증을받음
            })
            .formLogin(Customizer.withDefaults());//로그인 방식: 디폴트 방식
        return httpSecurity.build();
   }
}
