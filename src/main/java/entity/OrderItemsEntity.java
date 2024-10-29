package entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`orderItems`")
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "order_id", referencedColumnName = "orderID")
    private OrderEntity orderEntity;

    private String productName;
    private String productId;
    private String categoryId;
    private String categoryName;
    private String supplierId;
    private String supplierName;

    private Double unitPrice;
    private String size;
}
