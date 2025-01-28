package de.wetie.springboot.controller.config;

import de.wetie.springboot.service.impl.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // authentication bean
    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // to inform spring Framework what type of authentication we are using. Hier is DB specific authentication
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    // Authorization Bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> {
                    // authorize the urls based on urls
                    requests.requestMatchers("/appUsers").permitAll();
                    requests.requestMatchers("book-restapi/admin/**").hasAnyRole("ADMIN", "USER");
                    requests.requestMatchers("book-restapi/user/**").hasAnyRole("ADMIN");
                })
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider());


        return http.build();
    }
}


// https://www.youtube.com/watch?v=CgUxrT7BZ_Q&list=PLcs1FElCmEu0sH0_1vmNk6ubfrVv0OR3h&index=3     (User Authentication with Spring Security in Spring Boot Tutorial | Java)


























