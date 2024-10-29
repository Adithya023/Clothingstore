package entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "`product`")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String productId;
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private CategoryEntity categoryEntity;

    private Integer quantity;
    private Double unitPrice;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplierId")
    private SupplierEntity supplierEntity;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.productId = "P" + String.format("%04d",id);
        }
    }
}
