package com.example.phonebookmanager.config;

import com.example.phonebookmanager.domain.entity.RoleType;
import com.example.phonebookmanager.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl detailService;

    public SecurityConfig(UserDetailServiceImpl detailService) {
        this.detailService = detailService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.POST, "/api/user/signup/**").permitAll()
                        .antMatchers("/api/admin/**").hasAuthority(RoleType.ADMIN.name())
                        .antMatchers("/api/user/**").hasAuthority(RoleType.USER.name())
                        .anyRequest().authenticated())
                .userDetailsService(detailService)
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
