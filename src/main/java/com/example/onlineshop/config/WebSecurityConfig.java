package com.example.onlineshop.config;

import com.example.onlineshop.service.impl.UserDetailsServiceImp;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/*", "/exit").hasRole("ADMIN")
                .antMatchers("/user/*", "/exit").hasRole("USER")
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((req, res, auth) -> {
                    res.sendRedirect("/");
                })
                .failureHandler((req, res, exp) -> {
                    String message = "";
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        message = "Wrong email or password";
                    } else {
                        logger.error(exp.getMessage());
                    }
                    req.getSession().setAttribute("message", message);
                    res.sendRedirect("/login");
                })
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/exit"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();
    }
}
