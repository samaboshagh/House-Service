package org.example.finalprojectphasetwo.config;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService<User> userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/customer/sing_up").permitAll()
                        .requestMatchers("/specialist/sing_up").permitAll()
                        .requestMatchers("admin/**").hasRole("ADMIN")
                        .requestMatchers("customer/**").hasAnyRole("CUSTOMER")
                        .requestMatchers("specialist/**").hasRole("SPECIALIST")
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((userName) -> userService
                        .findByUsernameOptional(userName)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", userName))))
                .passwordEncoder(passwordEncoder);
    }

}