package utn.tacs.grupo5.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean admin;
}
