package utn.tacs.grupo5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SpringSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(
                        csrf -> csrf.csrfTokenRepository(
                                CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .ignoringRequestMatchers("/**") // Disable CSRF for API
                                                                // routes
                )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/**").permitAll());
        return http.build();
    }
}
