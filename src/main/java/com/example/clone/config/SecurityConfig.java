package com.example.clone.config;

import com.example.clone.repository.UserInfoRepository;
import com.example.clone.common.JwtAuthFilter;
import com.example.clone.service.UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.clone.service.JwtService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserInfoRepository repository, PasswordEncoder encoder, UserInfoService userInfoService,JwtService jwtService) throws Exception {
        JwtAuthFilter authFilter = new JwtAuthFilter(jwtService, userInfoService);

        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/welcome", "/auth/addNewUser", "/auth/generateToken").permitAll()
                .and()

                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/comment").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/posts/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/users/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider(repository, encoder, userInfoService))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserInfoRepository repository, PasswordEncoder encoder, UserInfoService userInfoService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userInfoService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
