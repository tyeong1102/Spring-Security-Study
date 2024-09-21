package com.example.testsecurity.config;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filerChain(HttpSecurity http) throws Exception {

        // 위에서부터 순서대로 진행되기 때문에 순서에 유의해야 한다.
        http
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/", "/login").permitAll() // 모두 접근 가능
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 해당 역할을 가지고 있어야 함.
                    .anyRequest().authenticated() // 로그인된 사용자만 접근할 수 있도록
                );

        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());


        return http.build();
    }
}
