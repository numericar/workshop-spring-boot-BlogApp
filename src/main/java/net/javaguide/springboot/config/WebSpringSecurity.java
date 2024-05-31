package net.javaguide.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // enable spring security, can config security fetures
public class WebSpringSecurity {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf -> csrf.disable()) // csrf use only in bank
    		.authorizeHttpRequests(authorize -> authorize
    											.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
    											.requestMatchers("/auth/register/**").permitAll()
    										    .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("ADMIN", "GUEST"))
    		.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
    		.formLogin(form -> form.loginPage("/auth/login").loginProcessingUrl("/auth/login").defaultSuccessUrl("/admin/posts").permitAll())
    		.logout(config -> config.permitAll());
    	
    	return http.build();
    }
}
