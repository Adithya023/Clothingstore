package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItems {
    private Order order;
    private String productName;
    private String productId;
    private String categoryId;
    private String categoryName;
    private String supplierId;
    private String supplierName;
    private Double unitPrice;
    private String size;
}
