package com.colletech.municipality.security;

public class SecurityConstants {

	private SecurityConstants() {
		throw new IllegalStateException("Clase de constantes de seguridad");
	}
	
//	SPRING SECURITY
	public static final String LOGIN_URL = "/login";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String ID = "Colletech";
	public static final String ISSUER_INFO = "https://colletech.pe/";
	
//	JWT
	public static final String SECRET_KEY = "C0ll3te|4";
	public static final Long TOKEN_EXPIRATION_TIME = 1_000L * 60 * 60 * 1; // 1Hora
}
