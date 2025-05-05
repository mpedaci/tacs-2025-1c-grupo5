package utn.tacs.grupo5.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(authInputDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credentials invalid");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthOutputDto(token);
    }


    @Override
    public AuthOutputDto refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token invalid");
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newToken = jwtUtil.generateToken(user);

        return new AuthOutputDto(newToken);
    }

    @Override
    public void logout(String refreshToken) {
    }
}
