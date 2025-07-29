package com.cse.journalApp.config;

import com.cse.journalApp.service.userdetailserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  //  It tells Spring that the class contains methods that produce beans, which should be managed by the Spring IoC container.
@EnableWebSecurity   // this means we are changing the default security configuration which is given by spring to our own method of authentication
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    userdetailserviceimpl userdetailservice;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/journal/**","/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN") // will  authenticate those entities who have role as ADMIN
                .anyRequest().permitAll()
                .and()
                        .httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailservice).passwordEncoder(Encoder());   //defining how password will be encrypted.

    }
    @Bean
    public PasswordEncoder Encoder(){
        return new BCryptPasswordEncoder();
    } //it is the built in method for encode the password.
}

