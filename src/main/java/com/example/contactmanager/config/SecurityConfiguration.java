package com.example.contactmanager.config;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.service.UserDetailServiceImpl;
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
public class SecurityConfiguration {


    private final UserDetailServiceImpl detailService;

    public SecurityConfiguration(UserDetailServiceImpl detailService) {
        this.detailService = detailService;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
                        .antMatchers("/api/admin/**").hasAuthority(RoleType.ADMIN.name())
                        .antMatchers("/api/test/user").hasAnyAuthority(RoleType.USER.name(), RoleType.ADMIN.name())
                        .antMatchers("/api/test/admin").hasAuthority(RoleType.ADMIN.name())
                        .antMatchers("/api/admin/contact_type/**").hasAuthority(RoleType.ADMIN.name())
                        .antMatchers("/api/admin/user/**").hasAuthority(RoleType.ADMIN.name())
                        .antMatchers("/api/admin/contact_type/**").hasAuthority(RoleType.ADMIN.name())
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
