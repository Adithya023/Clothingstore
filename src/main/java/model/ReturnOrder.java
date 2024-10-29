package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnOrder {
    private String returnOrderId;
    private Order order;
    private Product product;
    private Integer quantity;
    private Double amount;
}
