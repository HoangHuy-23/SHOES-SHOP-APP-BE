package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.RoomItem;
import iuh.fit.dhktpm117ctt.group06.repository.RoomItemRepository;
import iuh.fit.dhktpm117ctt.group06.services.RoomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomItemServiceImpl implements RoomItemService {
    private RoomItemRepository roomItemRepository;

    @Autowired
    public RoomItemServiceImpl(RoomItemRepository roomItemRepository) {
        this.roomItemRepository = roomItemRepository;
    }

    @Override
    public Optional<RoomItem> findById(String s) {
        return roomItemRepository.findById(s);
    }

    @Override
    public List<RoomItem> findByName(String name) {
        return roomItemRepository.findByName(name);
    }

    @Override
    public List<RoomItem> findAll() {
        return roomItemRepository.findAll();
    }

    @Override
    public RoomItem save(RoomItem roomItem) {
        return roomItemRepository.save(roomItem);
    }


    @Override
    public void deleteById(String id) {
        roomItemRepository.deleteById(id);
    }

    @Override
    public RoomItem update(RoomItem roomItem) {
        return roomItemRepository.save(roomItem);
    }
}
