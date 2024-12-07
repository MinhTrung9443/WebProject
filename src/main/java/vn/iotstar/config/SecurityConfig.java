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
		http.csrf().disable().authorizeRequests().requestMatchers("/public/**").permitAll() // Cho phép truy cập công
																							// khai
				.anyRequest().authenticated() // Yêu cầu đăng nhập cho các request khác
				.and().formLogin().loginPage("/login") // Đặt trang đăng nhập tùy chỉnh
				.loginProcessingUrl("/login") // URL để xử lý đăng nhập
				.defaultSuccessUrl("/home", true) // Redirect sau khi đăng nhập thành công
				.failureUrl("/login?error=true") // Redirect khi đăng nhập thất bại
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/public/goodbye");
		return http.build();
	}*/
}
