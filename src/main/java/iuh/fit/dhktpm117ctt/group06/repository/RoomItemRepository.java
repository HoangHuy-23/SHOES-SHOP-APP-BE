package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.RoomItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomItemRepository extends JpaRepository<RoomItem,String> {
    Optional<RoomItem> findById(String s);
    List<RoomItem> findByName(String name);
}
