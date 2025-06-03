package utn.tacs.grupo5.security;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utn.tacs.grupo5.service.IAuthService;

@Component
@Profile("!test")
public class JwtFilter extends OncePerRequestFilter {

    private final IAuthService authService;

    public JwtFilter(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            if (!authService.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Unauthorized\"}");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Access Denied\"}");
            return;
        }

        Authentication auth = authService.getAuthentication(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        List<String> excludedPaths = List.of(
                "/auth",
                "/docs",
                "/api-docs",
                "/swagger-ui",
                "/error",
                "/health-check");

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Exclude paths from excludedPaths or user creation (POST /api/users)
        return excludedPaths.stream().anyMatch(excludedPath -> path.startsWith("/api" + excludedPath))
                || ("POST").equals(method) && ("/api/users").equals(path);
    }

}
