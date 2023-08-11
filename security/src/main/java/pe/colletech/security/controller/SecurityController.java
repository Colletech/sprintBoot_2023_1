package pe.colletech.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.colletech.security.config.util.JwtUtil;
import pe.colletech.security.config.util.SecurityConstants;
import pe.colletech.security.dao.GenericUsernamePassword;
import pe.colletech.security.dao.User;
import pe.colletech.security.services.IUserService;
import pe.colletech.security.services.MiUserDetailsService;

@RestController
public class SecurityController {

	@Autowired
	private IUserService userService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private MiUserDetailsService miUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarse(@Valid @RequestBody(required = true) User usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		usuario = userService.registrarUsuario(usuario);
		usuario.setPassword("*****");
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}

	@PostMapping(SecurityConstants.LOGIN_URL)
	public ResponseEntity<?> iniciarSesion(@RequestBody GenericUsernamePassword autLogin) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(autLogin.getUsername(), autLogin.getPassword()));
		} catch (BadCredentialsException ex) {
			response.put(ex.getMessage(), "Error en el username o contraseña");
			return ResponseEntity.ok(response);
		}
		// Obtenemos los datos del usuario de la BD para construir el token
		final UserDetails userDetails = miUserDetailsService.loadUserByUsername(autLogin.getUsername());
		response.put("token", SecurityConstants.PREFIX.concat(jwtUtil.creatToken(userDetails)));
		// Regresamos el token
		return ResponseEntity.ok(response);
	}
}
