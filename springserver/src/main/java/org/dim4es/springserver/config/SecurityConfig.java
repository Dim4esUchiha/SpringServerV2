package org.dim4es.springserver.config;

import org.dim4es.springserver.security.JwtService;
import org.dim4es.springserver.security.UserDetailsServiceImpl;
import org.dim4es.springserver.security.filter.JwtAuthenticationFilter;
import org.dim4es.springserver.security.provider.DbAuthenticationProvider;
import org.dim4es.springserver.security.provider.JwtAuthenticationProvider;
import org.dim4es.springserver.services.UserInfoDetailsService;
import org.dim4es.springserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserInfoDetailsService userInfoDetailsService;

    @Autowired
    public SecurityConfig(UserInfoDetailsService userInfoDetailsService) {
        this.userInfoDetailsService = userInfoDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .formLogin().disable()
                .csrf().disable()
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsServiceImpl(userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider(JwtService jwtService,
                                                            UserDetailsService userDetailsService) {
        return new JwtAuthenticationProvider(jwtService, userDetailsService);
    }

    @Bean
    public AuthenticationProvider dbAuthenticationProvider(UserDetailsService userDetailsService,
                                                           PasswordEncoder passwordEncoder) {
        return new DbAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
