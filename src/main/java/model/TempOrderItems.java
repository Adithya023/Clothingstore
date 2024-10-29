package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TempOrderItems {
    private OrderItems orderItem;
    private Integer quantity;
}
