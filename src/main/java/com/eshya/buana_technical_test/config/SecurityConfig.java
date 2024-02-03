package com.eshya.buana_technical_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired



    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");
//        http.cors()
//            .and()
//            .authorizeRequests(a -> a
//                .antMatchers("/", "/error", "/webjars/**","/user","/access-token").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .anyRequest().authenticated()
//            )
//            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//            .logout(l -> l
//                    .logoutSuccessUrl("/").permitAll()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//            )
//            .csrf(c -> c
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            )
//            .oauth2Login(o -> o
//                    .failureHandler((request, response, exception) -> {
//                        request.getSession().setAttribute("error.message", exception.getMessage());
//                        handler.onAuthenticationFailure(request, response, exception);
//                    })
//
//            );

        http.authorizeRequests()
                .antMatchers("/**").fullyAuthenticated()
                .antMatchers("/", "/error", "/webjars/**","/user","/access-token").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer().jwt()
                .and()
                .and()
                .cors().and().csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );
    }



}
