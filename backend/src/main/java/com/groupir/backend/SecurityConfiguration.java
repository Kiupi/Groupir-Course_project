package com.groupir.backend;

import com.groupir.backend.model.Role;
import com.groupir.backend.security.JwtConfigurer;
import com.groupir.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                //OPTIONS request
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                //User Controller
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/list").hasAuthority("ADMIN")
                .antMatchers("/api/user/add/*").permitAll()
                .antMatchers("/api/user/delete/*").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/user/update/*").hasAnyAuthority("ADMIN", "USER", "SUPPLIER")
                .antMatchers("/api/user/get/*").hasAuthority("ADMIN")

                //Supplier Controller
                .antMatchers("/api/supplier/**").hasAnyAuthority("SUPPLIER")

                //PaymentMethod Controller
                .antMatchers("/api/user/*/payment-method/**").hasAnyAuthority("SUPPLIER", "ADMIN", "USER")

                // Product Controller
                .antMatchers("/api/product/add").hasAnyAuthority("ADMIN")
                .antMatchers("/api/product/delete/*").hasAnyAuthority("ADMIN")
                .antMatchers("/api/product/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/api/product/**").permitAll()


                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/**")
        ;
    }

}
