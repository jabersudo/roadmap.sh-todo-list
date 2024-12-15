package com.todolistapp.service.security;

import com.todolistapp.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    List<String> nonPublicPaths = new ArrayList<>(List.of(
            HttpMethod.POST.name() + " /api/v1/todos",
            HttpMethod.PUT.name() + " /api/v1/todos",
            HttpMethod.GET.name() + " /api/v1/todos",
            HttpMethod.DELETE.name() + " /api/v1/todos"
    ));

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                if (isPublicPath(request)) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    throw new UnauthorizedException("Unauthorized");
                }
            }
            jwt = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                throw new UnauthorizedException("Unauthorized");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new UnauthorizedException("Unauthorized");
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private boolean isPublicPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String url = method + " " + requestURI;
        for (String nonPublicUrl : nonPublicPaths) {
            if (url.startsWith(nonPublicUrl)) {
                return false;
            }
        }
        return true;
    }

}
