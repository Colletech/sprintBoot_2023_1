package pe.colletech.security.config.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	Logger log = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${token.palabra.secreta}")
	private String SECRETO;

	/**
	 * Este metodo solo extrae una parte del token [header.payload.signature] siendo
	 * los claims registrados los claims (iss, sub, aude, exp)
	 * 
	 * @param token requiere el token para extraer la parte del claim
	 * @return El conjunto de Claims
	 */
	public Claims extraerContenidoClaims(String token) {
//		 parser: convierte a String, establece la clave para determinar si el JWT es valido dentro del header
		return Jwts.parser().setSigningKey(SECRETO.getBytes()).parseClaimsJws(token).getBody();
	}

	/**
	 * Extrae del claim el contenido de "exp" para identificar el tiempo de
	 * vencimiento del token
	 * 
	 * @param token token a extraer el tiempo de vencimiento
	 * @return La fecha de vencimiento.
	 */
	public Date extraerTiempoVencimiento(String token) {
		return extraerContenidoClaims(token).getExpiration();
	}

	/**
	 * Determina si se acepta procesar o no el token de acuerdo a la fecha y hora
	 * actual
	 * 
	 * @param token Del que se extrae la fecha de vencimiento
	 * @return si ya vencio o no el token
	 */
	public boolean isTokenExpiration(String token) {
		return extraerTiempoVencimiento(token).before(new Date());
	}

	/**
	 * Metodo que preparar la estructura del payload poblandolo con el contenido de
	 * los claims (sub, issu, exp, etc): <b>builder:</b> Construye la estructura del
	 * payload. <b>setSubject:</b> Establece el nombre del usuario al payload.
	 * <b>setIssuedAt:</b> Establece el tiempo en que se crea. <b>setExpiration:</b>
	 * Tiempo que va a durar el token. <b>signWith:</b> Cifra la palabra secreta con
	 * el algoritmo que se especifica
	 * 
	 * @param payload Sera el payload que sera usado para construir los claims sobre
	 *                el.
	 * @param subject Establece el username del token
	 * @return el String del payload del token
	 */
	public String prepararEstructuraToken(Map<String, Object> payload, String subject) {
		return Jwts.builder()
//				indico los roles que tiene
				.setClaims(payload)
//				identificador del token
				.setId(SecurityConstants.ID)
//				informacion al token
				.setIssuer(SecurityConstants.ISSUER_INFO)
//				agrego informacion del usuario
				.setSubject(subject)
//				fecha de creacion
				.setIssuedAt(new Date(System.currentTimeMillis()))
//				compresion de informacion del token
				.compressWith(CompressionCodecs.DEFLATE)
//				fecha de expiracion
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_TIME))
//				algoritmo de cifrado
				.signWith(SignatureAlgorithm.HS512, SECRETO.getBytes()).compact();
	}

	/**
	 * Es el encargado de crear el token por medio del <code>UserDetails</code> dado
	 * como parametro invoca al metodo que construye la estructura (payload)-
	 * 
	 * @param userDetails para obtener el username del usuario autenticado.
	 * @return el token [header.payload.signature]
	 * @throws JsonProcessingException
	 */
	public String creatToken(UserDetails userDetails) throws JsonProcessingException {
		Map<String, Object> claims = new HashMap<>();
//		indico los roles que tiene
		claims.put(SecurityConstants.AUTHORITIES, new ObjectMapper().writeValueAsString(userDetails.getAuthorities()));
		return prepararEstructuraToken(claims, userDetails.getUsername());
	}

	/**
	 * Validar el token
	 * 
	 * @param token       token al que se le extraera el claim subject.
	 * @param userDetails detalles del usuario
	 * @return el username del token
	 */
	public boolean validarToken(String token, UserDetails userDetails) {
		final String username = extraerUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpiration(token));
	}

	/**
	 * Extrae del claim el nombre que contiene el subject
	 * 
	 * @param token token al que se le extraera el claim subject.
	 * @return el username del token
	 */
	public String extraerUsername(String token) {
		return extraerContenidoClaims(token).getSubject();
	}
}
