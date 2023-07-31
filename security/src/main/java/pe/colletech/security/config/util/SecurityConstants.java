package pe.colletech.security.config.util;

public class SecurityConstants {

	private SecurityConstants() {
		throw new IllegalStateException("Clase de constantes de seguridad");
	}

	// SPIRNG SECURITY
	public static final String LOGIN_URL = "/login";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String AUTHORITIES = "authorities";
	public static final String ISSUER_INFO = "https://colletech.pe/";
	public static final String ID = "Colletech";

	// JWT
//	public static final String SECRET_KEY = "C0ll3te|4";
	public static final long TOKEN_EXPIRATION_TIME = 1_000L * 60 * 60 * 1; // 1 hora
}
