package utn.tacs.grupo5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "User Schema", description = "User schema")
public class UserDto {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
