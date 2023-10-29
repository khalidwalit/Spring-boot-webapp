package com.project.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorized) ->
                        authorized
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/students/**").hasAnyRole("STUDENT", "TEACHER")
                                .requestMatchers("/teachers/**").hasAnyRole("TEACHER")
                                .requestMatchers("/teacher/**").hasAnyRole("TEACHER")

                ).formLogin(
                        form -> form
                                .loginPage("/index")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    // Determine the user's role and redirect accordingly
                                    if (authentication != null && authentication.getAuthorities().stream()
                                            .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
                                        response.sendRedirect("/teachers");
                                    } else if (authentication != null && authentication.getAuthorities().stream()
                                            .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                                        response.sendRedirect("/other");
                                    } else {
                                        response.sendRedirect("/students");
                                    }
                                })
                                .permitAll()
                ).logout(
                        logout-> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
