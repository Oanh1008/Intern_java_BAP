package com.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/room/showRooms").permitAll()
                .antMatchers("/room/new/**").hasRole("ADMIN")
                .antMatchers("/room/delete/**").permitAll()
                .antMatchers("/room/edit/**").permitAll()
                .anyRequest().authenticated().and().formLogin()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
        ;
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        // store in memory
        auth.inMemoryAuthentication()
                .withUser(users.username("user").password("123").roles("USER"))
                .withUser(users.username("admin").password("123").roles("USER", "ADMIN"));

    }
}
