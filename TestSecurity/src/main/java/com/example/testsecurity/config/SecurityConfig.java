package com.example.testsecurity.config;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filerChain(HttpSecurity http) throws Exception {

        // 위에서부터 순서대로 진행되기 때문에 순서에 유의해야 한다.
        http
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/", "/login", "/loginProc","/join", "joinProc").permitAll() // 모두 접근 가능
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 해당 역할을 가지고 있어야 함.
                    .anyRequest().authenticated() // 로그인된 사용자만 접근할 수 있도록
                );

//        http
//                .formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll()
//                );
//
//        http
//                .sessionManagement((auth) -> auth
//                        .maximumSessions(1) // 하나의 아이디에서 최대 중복 로그인 가능 개수.
//                        .maxSessionsPreventsLogin(true)); // 다중 로그인 개수 초과하였을 경우 판단.
//
//        http
//                .sessionManagement((auth) -> auth
//                        .sessionFixation().changeSessionId());
        http
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
