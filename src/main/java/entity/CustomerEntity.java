package entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`customer`")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String customerId;
    private String name;
    private String email;
    private String phoneNumber;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.customerId = "U" + String.format("%04d",id);
        }
    }
}
