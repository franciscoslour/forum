package br.com.alura.forum.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.controller.security.TokenService;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UsuarioRepository repository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {

		String token = recuperarToken(request);
		Boolean isTokenValido = tokenService.isTokenValido(token);
		
		if (isTokenValido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}

	
	private void autenticarCliente(String token) {
		Long idUsuario = this.tokenService.getIdUsuario(token);
		Usuario usuario = this.repository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length());
	}

	
	
}
