package ru.saynurdinov.moviefan.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.saynurdinov.moviefan.DTO.UserInfoDTO;

import java.io.IOException;
import java.util.stream.Collectors;

@Component("tokenFilter")
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException, ExpiredJwtException {
        String authHeader = request.getHeader("Authorization");
        String login = null;
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            login = jwtUtils.getLogin(jwtToken);
        }
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserInfoDTO userInfo = new UserInfoDTO(jwtUtils.getId(jwtToken), login);
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userInfo, null,
                    jwtUtils.getRoles(jwtToken).stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
