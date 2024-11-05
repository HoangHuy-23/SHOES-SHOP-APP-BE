package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.RoomType;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> findAll();

    List<RoomType> findByName(String name);


    RoomType save(RoomType roomType);

    RoomType update(RoomType roomType);

    void delete(String id);
}
