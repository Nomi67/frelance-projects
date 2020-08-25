/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.management.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Nauman
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsProtocolService userDetailService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(corsConfigurationSource()).and().authorizeRequests()
                .antMatchers("/auth/login","/password/request").permitAll()
                .antMatchers("/company/names","/auth/permissions","/auth/user").authenticated()
                .antMatchers("/company/all","/company/create","/company/company-filter","/company/change-status","/company/edit").hasAnyAuthority("ACCESS_CREATE_COMPANY")
                .antMatchers("/location/company-locations","/department/by-location").hasAnyAuthority("ACCESS_LOCATION")
                .antMatchers("/permission/by-role").hasAuthority("ACCESS_ROLE_MANAGEMENT")
                .antMatchers("/role","/user/change-role","/user/filter").hasAuthority("ACCESS_PERMISSION_ASSIGNING")
                .antMatchers("/password/change","/password/get-all","/user/add","/user/disable").hasAnyAuthority("ACCESS_USER_MANAGE")
                .antMatchers("/logs").hasAuthority("ACCESS_USER_LOGS")
//                .antMatchers("/customer/AllCustomers", "/log/admin", "/log/get-vendors", "/log/delete-vendor")
//                .hasRole("ADMIN")
//                .antMatchers("/order/book-table", "/order/order-food", "/order/get-orders-by-customer",
//                        "/order//get-table-orders-customer")
//                .hasRole("USER")
//                .antMatchers("/order/**", "/order/get-order-by-status", "/order/get-all-stats",
//                        "/order/change-order-status", "/vendor/recipe-save")
//                .hasAnyRole("ADMIN", "VENDOR").antMatchers("/log/user").hasAnyRole("ADMIN", "USER").antMatchers("/")
//                .authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/auth/login").and().ignoring()
                .antMatchers(HttpMethod.GET, "/auth/user").and().ignoring();
//                .antMatchers(HttpMethod.GET, "/vendor/categorye").and().ignoring()
//                .antMatchers(HttpMethod.GET, "/log/contact-us");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        // the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
