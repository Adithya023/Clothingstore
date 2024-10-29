package entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`category`")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String categoryId;
    private String name;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.categoryId = "C" + String.format("%04d",id);
        }
    }
}
