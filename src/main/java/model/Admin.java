package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    private String adminId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String phoneNumber;
    private String password;
}
