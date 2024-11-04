package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findById(String s);
    List<Room> findByRatingGreaterThanEqual(double rating);
    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByFloorNumber(int floorNumber);
}