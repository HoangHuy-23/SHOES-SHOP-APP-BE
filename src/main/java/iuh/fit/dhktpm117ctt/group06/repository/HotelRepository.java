package iuh.fit.dhktpm117ctt.group06.repository;



import iuh.fit.dhktpm117ctt.group06.entities.Hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, String> {

    // Tìm tất cả khách sạn có tên chứa chuỗi name

    List<Hotel> findByNameContaining(String name);

    // Tìm tất cả khách sạn quản lý bởi adminId

    List<Hotel> findByAdminId(String adminId);

    // Tìm tất cả khách sạn dựa trên đánh giá

    List<Hotel> findByRating(float rating);

    // Xóa khách sạn theo id

    void deleteById(String id);
}