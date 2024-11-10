package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double amount;
    private LocalDateTime paymentDate;
    private String transactionId;
    private String content;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
