package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.RoomItem;

import java.util.List;
import java.util.Optional;

public interface RoomItemService {
    Optional<RoomItem> findById(String s);

    List<RoomItem> findByName(String name);

    List<RoomItem> findAll();

    RoomItem save(RoomItem roomItem);

    void deleteById(String id);

    RoomItem update(RoomItem roomItem);
}
