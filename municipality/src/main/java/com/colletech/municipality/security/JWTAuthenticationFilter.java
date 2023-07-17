package com.colletech.municipality.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

//	Intento de ingreso
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
//			Informacion enviado en el body de la solicitud como RAW
			User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					credentials.getUsername(), credentials.getPassword(), Collections.emptyList());
			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<>();
		body.put("mensaje", "Credenciales ingresadas son erróneas");
		body.put("error", failed.getMessage());
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
//		mostramos la respuesta en un JSON
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		no esta autorizado
		response.setStatus(401);
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
//		obtengo la clase del logueo exitoso
		User user = (User) auth.getPrincipal();
		Map<String, Object> body = new HashMap<>();
		Claims claims = Jwts.claims();
		claims.put("", null);
		String token = Jwts.builder()
//				indicar los roles
//				.setClaims(claims)
//				identificador del token
				.setId(SecurityConstants.ID)
//				informaction del token
				.setIssuer(SecurityConstants.ISSUER_INFO)
//				informacion del usuario
				.setSubject(user.getUsername())
//				fecha de creacion del token
				.setIssuedAt(new Date())
//				compresion de la informacion del token
				.compressWith(CompressionCodecs.DEFLATE)
//				fecha de expiracion del token
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_TIME))
//				algoritmo de cifrado
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY.getBytes()).compact();
		response.addHeader(SecurityConstants.HEADER, SecurityConstants.PREFIX.concat(token));
		body.put("usuario", user);
		body.put("token", token);
		body.put("TokenType", SecurityConstants.PREFIX.trim());
		body.put("mensaje", "Se ha logueado satisfactoriamente");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
//		la respuesta se muestra en formato JSON
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		indico que fue exitoso
		response.setStatus(200);
	}
}
