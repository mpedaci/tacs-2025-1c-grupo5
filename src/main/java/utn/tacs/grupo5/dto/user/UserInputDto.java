package utn.tacs.grupo5.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This dto represents the user data received to create a new user.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(name = "User Input Schema", description = "User schema for input")
/**
 * This dto represents the data received to create a new user.
 */
public class UserInputDto extends UserBaseDto {

    private String password;

    public UserInputDto(UserBaseDto userBaseDto, String password) {
        super(
                userBaseDto.getEmail(),
                userBaseDto.getPhone(),
                userBaseDto.getFirstName(),
                userBaseDto.getLastName(),
                userBaseDto.getUsername());
        this.password = password;
    }

}
