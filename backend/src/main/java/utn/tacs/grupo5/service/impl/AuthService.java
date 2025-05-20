package utn.tacs.grupo5.service.impl;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import utn.tacs.grupo5.controller.exceptions.CredentialException;
import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.repository.UserRepository;
import utn.tacs.grupo5.security.JwtUtil;
import utn.tacs.grupo5.service.IAuthService;

@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthOutputDto login(AuthInputDto authInputDto) {
        var user = userRepository.findByUsername(authInputDto.getUsername())
                .orElseThrow(() -> new CredentialException("User. not found or password invalid"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(authInputDto.getPassword(), user.getPassword())) {
            throw new CredentialException("User not found or password. invalid");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthOutputDto(token);
    }

    @Override
    public AuthOutputDto refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new CredentialException("Invalid token");
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CredentialException("User not found"));
        String newToken = jwtUtil.generateToken(user);

        return new AuthOutputDto(newToken);
    }

    @Override
    public void logout(String refreshToken) {
    }

    @Override
    public Boolean validateToken(String token) {
        if (token == null) {
            throw new CredentialException("No token provided");
        }
        return jwtUtil.validateToken(token);
    }

    @Override
    public Authentication getAuthentication(String token) {
        if (token == null) {
            throw new CredentialException("Access Denied");
        }
        String username = jwtUtil.getUsernameFromToken(token);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CredentialException("Access Denied"));

        List<SimpleGrantedAuthority> authorities = user.getAdmin() ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, "",
                authorities);

        return authentication;
    }
}
