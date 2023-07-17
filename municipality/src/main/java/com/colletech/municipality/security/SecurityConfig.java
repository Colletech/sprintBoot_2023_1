package com.colletech.municipality.security;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtEntryPoint entryPoint;
	
	@ConditionalOnMissingBean
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	configuracion de cors
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
//		permite el envio de credenciales entre dominios
		configuration.setAllowCredentials(true);
//		acepta informacion en la cabecera
		configuration.setAllowedHeaders(Arrays.asList("*"));
//		url que puede pedir informacion
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//		metodos http a los que tiene acceso
		configuration.setAllowedMethods(Arrays.asList("*"));
//		duracion de la informacion en cache
		configuration.setMaxAge(Duration.ofMinutes(15));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		Se desactiva el uso de cookies
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		Cords por defecto
		.and().cors()
//		Se desactiva el filtro CSRF
		.and().csrf().disable()
//		configurar el mensaje para el punto de acceso
		.exceptionHandling().authenticationEntryPoint(null).and()
//		Se indica que el resto de URLs esten seguras
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
		.addFilter(new JWTAuthorizationFilter(authenticationManager())).authorizeRequests().anyRequest().authenticated();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
//		Se indica que el Swagger no requiere autenticacion
//		no se agrega login
		web.ignoring().antMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**");
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		Se define la clase que recupera los usuarios y el algoritmo para procesar los paswwords
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
}
