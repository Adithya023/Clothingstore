package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String productId;
    private String name;
    private Category category;
    private Integer quantity;
    private Double unitPrice;
    private Supplier supplier;
}
