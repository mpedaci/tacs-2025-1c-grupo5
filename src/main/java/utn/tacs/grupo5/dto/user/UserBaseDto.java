package utn.tacs.grupo5.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserBaseDto {

    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;

}
