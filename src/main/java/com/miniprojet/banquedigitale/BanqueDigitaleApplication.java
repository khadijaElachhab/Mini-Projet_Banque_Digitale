package com.miniprojet.banquedigitale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class BanqueDigitaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueDigitaleApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
        return args -> {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin")
                                .password(passwordEncoder.encode("admin123"))
                                .roles("ADMIN", "USER")
                                .build()
                );

                jdbcUserDetailsManager.createUser(
                        User.withUsername("user")
                                .password(passwordEncoder.encode("user123"))
                                .roles("USER")
                                .build()
                );
        };
    }
}