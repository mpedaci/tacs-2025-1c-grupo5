package utn.tacs.grupo5.entity;

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
    // TODO: roles
}
