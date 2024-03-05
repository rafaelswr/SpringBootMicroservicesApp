package com.rafaelswr.apigateway.conig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth-> auth.pathMatchers("/eureka/**").permitAll().anyExchange().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer-> oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .build();
    }

}
