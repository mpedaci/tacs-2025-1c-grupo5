package utn.tacs.grupo5.dto.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This dto represents the user data returned by the API.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(name = "User Output Schema", description = "User schema for output")
public class UserOutputDto extends UserBaseDto {

    private Long id;

    @Schema(description = "Creation date of the user", example = "YYYY-MM-DD")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "Creation date of the user", example = "YYYY-MM-DD")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public UserOutputDto(UserBaseDto userBaseDto,
            Long id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(
                userBaseDto.getEmail(),
                userBaseDto.getPhone(),
                userBaseDto.getFirstName(),
                userBaseDto.getLastName(),
                userBaseDto.getUsername());
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
