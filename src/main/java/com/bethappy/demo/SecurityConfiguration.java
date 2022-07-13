package com.bethappy.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
    .dataSource(dataSource).passwordEncoder(getPasswordEncoder());
  }
  @Bean
  public BCryptPasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
        .cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/users/new").permitAll()
        .antMatchers("/users").permitAll()
        .antMatchers("/character").permitAll();
            // .anyRequest().authenticated()
            // .and()
            // .formLogin()
            // .loginPage("/login")
            // .and()
            // .logout()
            // .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            // .logoutSuccessUrl("/login?logout")
            // .invalidateHttpSession(true)
            // .and()
            // .exceptionHandling()
            // .accessDeniedPage("/403");
  }    
}
