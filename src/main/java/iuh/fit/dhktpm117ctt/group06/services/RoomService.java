package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Optional<Room> findById(String s);
    List<Room> findByRatingGreaterThanEqual(double rating);
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByFloorNumber(int floorNumber);
    List<Room> findAll();
    Room save(Room room);
    void deleteById(String id);
    Room update(Room room);
}
