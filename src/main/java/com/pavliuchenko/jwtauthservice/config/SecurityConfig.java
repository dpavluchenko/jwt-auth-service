package com.pavliuchenko.jwtauthservice.config;

import com.pavliuchenko.jwtauthservice.config.auth.basic.BasicAuthSuccessHandler;
import com.pavliuchenko.jwtauthservice.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/sign-up","/service/public").permitAll()
                .pathMatchers("/sign-in")
                .authenticated()
                .and()
                .addFilterAt(createBasicFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers("")
                .authenticated()
                .and()
                .addFilterAt(createJwtAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private WebFilter createBasicFilter() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        AuthenticationWebFilter filter = new AuthenticationWebFilter(manager);
        filter.setServerAuthenticationConverter(new ServerFormLoginAuthenticationConverter());
        filter.setAuthenticationSuccessHandler(new BasicAuthSuccessHandler());
        return filter;
    }

    private WebFilter createJwtAuthFilter() {
        return null;
    }

}