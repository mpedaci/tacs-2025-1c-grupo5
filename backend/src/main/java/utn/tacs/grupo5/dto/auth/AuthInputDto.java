package utn.tacs.grupo5.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Auth Input Schema", description = "Auth schema for input")
public class AuthInputDto {
    @Schema(description = "Username", example = "user")
    private String username;

    @Schema(description = "Password", example = "password")
    private String password;
}
