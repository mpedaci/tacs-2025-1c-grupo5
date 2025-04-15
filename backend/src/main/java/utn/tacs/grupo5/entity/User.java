package utn.tacs.grupo5.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: roles
}
