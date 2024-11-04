package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "BookingDetail")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date checkInDate;
    private Date checkOutDate;
    private int numOfAdult;
    private int numOfChildren;
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_item_id")
    private RoomItem roomItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
