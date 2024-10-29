package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "`orders`")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String orderId;
    private LocalDate date;
    private LocalTime time;
    private Double total;
    private String paymentType;
    private Integer orderItemCount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerID")
    private CustomerEntity customerEntity;

    @PostPersist
    public void addPrefixToId() {
        if (id != null) {
            this.orderId = "O" + String.format("%04d",id);
        }
    }
}
