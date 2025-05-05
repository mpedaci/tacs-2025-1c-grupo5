package utn.tacs.grupo5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utn.tacs.grupo5.controller.response.ResponseGenerator;
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
        return ResponseGenerator.generateResponseOK(token);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token")
    public ResponseEntity<AuthOutputDto> refresh(@RequestHeader("Authorization") String token) {
        AuthOutputDto tkn = authService.refreshToken(token);
        return ResponseGenerator.generateResponseOK(tkn);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user (optional token invalidation)")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseGenerator.generateResponseOK(null);
    }
}
