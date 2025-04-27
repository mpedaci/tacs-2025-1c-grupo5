package utn.tacs.grupo5.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class User {

    private UUID id;
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
