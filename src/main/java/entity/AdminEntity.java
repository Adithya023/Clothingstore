package entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "`admin`")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String adminId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String phoneNumber;
    private String password;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.adminId = "A" + String.format("%04d",id);
        }
    }
}
