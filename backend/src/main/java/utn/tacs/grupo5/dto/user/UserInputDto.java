package utn.tacs.grupo5.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This dto represents the user data received to create a new user.
 */
@Data
@NoArgsConstructor
@Schema(name = "User Input Schema", description = "User schema for input")
/**
 * This dto represents the data received to create a new user.
 */
public class UserInputDto {

    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

}
