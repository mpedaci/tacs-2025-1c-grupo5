package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.service.IAuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication operations")
public class AuthController extends BaseController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT")
    public ResponseEntity<AuthOutputDto> login(@RequestBody AuthInputDto authInputDto) {
        AuthOutputDto token = authService.login(authInputDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token")
    public ResponseEntity<AuthOutputDto> refresh(@RequestBody String refreshToken) {
        AuthOutputDto token = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user (optional token invalidation)")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
