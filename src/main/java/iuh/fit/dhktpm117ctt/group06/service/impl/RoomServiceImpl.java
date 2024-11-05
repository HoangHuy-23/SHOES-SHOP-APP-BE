package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Room;
import iuh.fit.dhktpm117ctt.group06.repository.RoomRepository;
import iuh.fit.dhktpm117ctt.group06.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;


    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<Room> findById(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> findByRatingGreaterThanEqual(double rating) {
        return roomRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public List<Room> findByFloorNumber(int floorNumber) {
        return roomRepository.findByFloorNumber(floorNumber);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(String id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room update(Room room) {
        // Kiểm tra xem phòng có tồn tại không trước khi cập nhật
        if (roomRepository.existsById(room.getId())) {
            return roomRepository.save(room);
        } else {
            throw new RuntimeException("Room not found with id: " + room.getId());
        }
    }
}
