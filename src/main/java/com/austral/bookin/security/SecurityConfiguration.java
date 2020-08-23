package com.austral.bookin.security;

import com.austral.bookin.service.authentication.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.austral.bookin.security.SecurityConstants.SIGN_UP_URL;

@Profile("!test")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(AuthenticationService authenticationService,
                                 PasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf()
                    .disable().authorizeRequests()
                    .antMatchers(SIGN_UP_URL).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}