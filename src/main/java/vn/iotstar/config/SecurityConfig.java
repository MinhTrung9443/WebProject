package vn.iotstar.config;


import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {
/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/register", "/login", "/forgot-password", "/reset-password").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .logout().permitAll();
        return http.build();
    }*/
}

