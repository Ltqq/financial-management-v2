package com.example.financialmanagementv2.config;

import com.example.financialmanagementv2.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginUserService loginUserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login.html", "/logout").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**", "/lib/**").permitAll();
        http.formLogin()
                //这个不是资源的路径地址，loginPage()中的地址是一个请求地址，也就是相对应的Controller层有相对应的处理流程
                .loginPage("/login.html")
                .loginProcessingUrl("/userLogin")
                .defaultSuccessUrl("/index")
                .failureHandler(new MyAuthenticationFailureHandler("/login.html"))
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login.html");
        http.authorizeRequests().anyRequest().authenticated();
    }
}
