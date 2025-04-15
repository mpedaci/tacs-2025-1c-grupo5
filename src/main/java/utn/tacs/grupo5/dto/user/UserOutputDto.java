package utn.tacs.grupo5.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This dto represents the user data returned by the API.
 */
@Data
@NoArgsConstructor
@Schema(name = "User Output Schema", description = "User schema for output")
public class UserOutputDto {

    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
