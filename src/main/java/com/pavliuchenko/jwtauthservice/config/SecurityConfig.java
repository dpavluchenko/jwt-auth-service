package com.pavliuchenko.jwtauthservice.config;

import com.pavliuchenko.jwtauthservice.config.auth.basic.BasicAuthSuccessHandler;
import com.pavliuchenko.jwtauthservice.config.auth.basic.FormLoginAuthenticationConverter;
import com.pavliuchenko.jwtauthservice.config.auth.jwt.JwtReactiveAuthenticationManager;
import com.pavliuchenko.jwtauthservice.config.auth.jwt.JwtToAuthenticationConverter;
import com.pavliuchenko.jwtauthservice.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/sign-up","/service/public").permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers("/sign-in")
                .authenticated()
                .and()
                .addFilterAt(createBasicFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers(getJwtPatterns())
                .authenticated()
                .and()
                .addFilterAt(createJwtAuthFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable();
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
        filter.setServerAuthenticationConverter(new FormLoginAuthenticationConverter());
        filter.setAuthenticationSuccessHandler(new BasicAuthSuccessHandler(jwtService));
        return filter;
    }

    private WebFilter createJwtAuthFilter() {
        JwtReactiveAuthenticationManager manager = new JwtReactiveAuthenticationManager();
        AuthenticationWebFilter filter = new AuthenticationWebFilter(manager);
        filter.setServerAuthenticationConverter(new JwtToAuthenticationConverter(jwtService));
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(getJwtPatterns()));
        return filter;
    }

    private String[] getJwtPatterns(){
        return new String[]{"/user-info"};
    }

}