package com.task.BookStore.config;

import com.task.BookStore.filter.JwtAuthenticationFilter;
import com.task.BookStore.filter.JwtUtil;
import com.task.BookStore.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationFilter jwtTokenFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                                .requestMatchers("/api/auth/**").permitAll() // Public endpoints for registration and login



                                .requestMatchers("/authors/{id}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers("/students/add-book").hasAuthority("ROLE_STUDENT")

                                .requestMatchers("/students/subscribe-to-author").hasAuthority("ROLE_STUDENT")
                                .requestMatchers("/students/unsubscribe-from-author").hasAuthority("ROLE_STUDENT")
                                .requestMatchers("/students/subscribed-authors").hasAuthority("ROLE_STUDENT")
                                .requestMatchers("/students/reading-list").hasAuthority("ROLE_STUDENT")
                                .requestMatchers("/students/test").hasAuthority("ROLE_STUDENT")

                                .requestMatchers("/books/readers").permitAll()
                                .requestMatchers("/books/books").hasAnyRole("AUTHOR","ADMIN")
                                .requestMatchers("/books/{name}").hasAnyRole("AUTHOR","ADMIN")




                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
