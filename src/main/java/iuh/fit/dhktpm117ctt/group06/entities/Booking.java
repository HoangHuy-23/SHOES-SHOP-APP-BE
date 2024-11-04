package iuh.fit.dhktpm117ctt.group06.entities;

import iuh.fit.dhktpm117ctt.group06.entities.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double totalPrice;
    private Date bookingDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @OneToOne(mappedBy = "booking")
    private Payment payment;

    @OneToMany(mappedBy = "booking")
    private List<BookingDetail> bookingDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
