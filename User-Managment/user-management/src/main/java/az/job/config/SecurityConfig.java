package az.job.config;

import az.auth.JwtService;
import az.auth.config.JwtAuthFilterConfiguresAdapter;
import az.auth.config.JwtAuthRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtAuthRequestFilter jwtAuthRequestFilter;


    @Bean
    public JwtAuthFilterConfiguresAdapter  jwtAuthFilterConfiguresAdapter() {
        return new JwtAuthFilterConfiguresAdapter(jwtAuthRequestFilter);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthFilterConfiguresAdapter jwtAuthFilterConfiguresAdapter) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth
                        -> {
                    auth
                            .requestMatchers("/create-user**", "/create-company**").permitAll()
                            .requestMatchers("/login**", "/swagger-ui/**").permitAll()
                            .anyRequest().permitAll();
                });

        http.apply(jwtAuthFilterConfiguresAdapter);
        return http.build();
    }
}
