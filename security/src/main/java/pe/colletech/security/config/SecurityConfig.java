package pe.colletech.security.config;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pe.colletech.security.config.util.SecurityConstants;
import pe.colletech.security.services.MiUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MiUserDetailsService userDetailsService;

	@Autowired
	private AuthFiltroToken authFiltroToken;

	@ConditionalOnMissingBean
	@Bean
	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	configuracion de cors
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
//		permite el envio de credenciales entre dominios
		configuration.setAllowCredentials(true);
//		acepta informacion en la cabecera
		configuration.setAllowedHeaders(Arrays.asList("*"));
//        url que puede pedir informacion
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        metodos HTTP a los que tiene acceso
		configuration.setAllowedMethods(Arrays.asList("*"));
//		duracion de la informacion en cache
		configuration.setMaxAge(Duration.ofMinutes(15));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        metodos a los que tiene acceso
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Indicamos que queremos una autenticacion personalizada en este caso definimos
	 * el comportamiento del <b>serDetailsService</b> en nuestra clase
	 * {@link MiUserDetailsService}, esto permite personalizar la autenticacion.
	 * Tambien indicamos que debe cifrar la contraseña que se cree o que se analice.
	 * 
	 * @param auth usado para indicar la autenticacion por medio de la BD.
	 * @throws Exception si existe un problema con la autenticacion.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder());
	}

	/**
	 * Configuramos las rutas y/o recursos que queremos proteger y cuales establacer
	 * de modo publico, ademas de configurar nuestros propios filtros, login o
	 * logout.
	 * 
	 * @param http URL usada para comparar el acceso
	 * @throws Exception Si no tiene acceso a los recursos
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Se desactiva el uso de cookies
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				 Cords por defecto
				.and().cors()
//				 Se desactiva el filtro CSRF
				.and().csrf().disable()
//				url de autorizacion
				.authorizeRequests().antMatchers("/security/registrarse", "/security"+SecurityConstants.LOGIN_URL).permitAll().anyRequest()
				.authenticated();
		// Indicamos que usaremos un filtro
		http.addFilterBefore(authFiltroToken, UsernamePasswordAuthenticationFilter.class);
	}
}
