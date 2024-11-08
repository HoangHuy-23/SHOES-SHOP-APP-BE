package iuh.fit.dhktpm117ctt.group06.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, String> {

    // Tìm tất cả khách sạn có tên chứa chuỗi name

    List<Hotel> findByNameContaining(String name);

    // Tìm tất cả khách sạn dựa trên đánh giá


    @Override
    Optional<Hotel> findById(String id);

    void deleteById(String id);
}