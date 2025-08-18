package br.com.guilhermetassinari.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // @Bean a method inside this, says to spring that it will use a method already managed by spring.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // The object is from the type <CsrfConfigurer<HttpSecurity>>
        // Abstract Http Configurer is the base class for many spring configurers
        // So, it is the same as doing csrf -> csrf(), using the disable() static method
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
