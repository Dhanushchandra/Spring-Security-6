package com.security.demo;

import com.security.demo.model.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(registry->{
            registry.requestMatchers("/home","/register/**").permitAll();
            registry.requestMatchers("/admin/**").hasRole("ADMIN");
            registry.requestMatchers("/user/**").hasRole("USER");
            registry.anyRequest().authenticated();
        })
                .formLogin(formLogin -> formLogin.permitAll())
                .build();
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails normalUser = User
//                .builder()
//                .username("user")
//                .password("$2a$12$XTBUIJqOprJaMmIEXttABuO8dqAxJizNdf.7JZWgiOTSoP33oqsBq")
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User
//                .builder()
//                .username("admin")
//                .password("$2a$12$XTBUIJqOprJaMmIEXttABuO8dqAxJizNdf.7JZWgiOTSoP33oqsBq")
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

        @Bean
    public UserDetailsService userDetailsService(){
            return myUserDetailsService;
        }

        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(myUserDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
