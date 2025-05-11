package utn.tacs.grupo5.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This dto represents the user data received to create a new user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User Input Schema", description = "User schema for input")
/**
 * This dto represents the data received to create a new user.
 */
public class UserInputDto {

    private String name;
    private String username;
    private String password;
    private boolean admin;

}
