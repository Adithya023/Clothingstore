package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "`supplier`")
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String supplierId;
    private String name;
    private String company;
    private String email;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.supplierId = "S" + String.format("%04d",id);
        }
    }
}
